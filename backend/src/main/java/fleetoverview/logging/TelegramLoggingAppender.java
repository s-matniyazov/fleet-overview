package fleetoverview.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
public class TelegramLoggingAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final int MAX_LEN = 3500;
    private static final int MAX_STACK_FRAMES = 5;

    private static final String APPLICATION_PACKAGE = "fleetoverview.";

    private static final DateTimeFormatter TS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    private static final ThreadLocal<Boolean> SENDING =
            ThreadLocal.withInitial(() -> false);

    private String token;
    private String chatId;
    private String appName = "FLEET OVERVIEW";

    private int connectTimeoutMs = 3000;
    private int readTimeoutMs = 5000;

    private RestClient restClient;
    private String url;

    @Override
    public void start() {

        if (token == null || token.isBlank()) {
            addError("Telegram appender not started: token is missing.");
            return;
        }

        if (chatId == null || chatId.isBlank()) {
            addError("Telegram appender not started: chatId is missing.");
            return;
        }

        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(Duration.ofMillis(connectTimeoutMs));
        factory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        this.restClient = RestClient.builder()
                .requestFactory(factory)
                .build();

        this.url = "https://api.telegram.org/bot%s/sendMessage"
                .formatted(token);

        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {

        // Send only WARN and ERROR to Telegram
        if (event.getLevel().levelInt < Level.WARN_INT) {
            return;
        }

        // Prevent recursive logging
        if (Boolean.TRUE.equals(SENDING.get())) {
            return;
        }

        SENDING.set(true);

        try {
            sendToTelegram(format(event));
        } catch (Exception e) {
            // Don't use log.error() here.
            // It would trigger the appender again.
            addError("Failed to send log to Telegram", e);
        } finally {
            SENDING.set(false);
        }
    }

    private void sendToTelegram(String message) {

        restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "chat_id", chatId,
                        "text", message,
                        "parse_mode", "HTML"
                ))
                .retrieve()
                .toBodilessEntity();
    }

    private String format(ILoggingEvent event) {

        String level = event.getLevel().toString();

        String emoji = switch (level) {
            case "ERROR" -> "🚨";
            case "WARN" -> "⚠️";
            default -> "ℹ️";
        };

        StringBuilder sb = new StringBuilder();

        sb.append(emoji)
                .append(" <b>")
                .append(escapeHtml(appName))
                .append(" — ")
                .append(level)
                .append("</b>\n\n");

        sb.append("🕐 <b>")
                .append(TS.format(event.getInstant()))
                .append("</b>\n");

        sb.append("📋 ")
                .append(escapeHtml(event.getFormattedMessage()))
                .append('\n');

        IThrowableProxy throwable =
                getRootCause(event.getThrowableProxy());

        if (throwable != null) {
            appendException(sb, throwable);
        }

        return truncate(sb.toString());
    }

    private void appendException(
            StringBuilder sb,
            IThrowableProxy throwable) {

        sb.append("\n")
                .append("<b>Exception:</b> ")
                .append(escapeHtml(
                        getSimpleClassName(throwable.getClassName())
                ))
                .append('\n');

        if (throwable.getMessage() != null
                && !throwable.getMessage().isBlank()) {

            sb.append("<b>Message:</b> ")
                    .append(escapeHtml(throwable.getMessage()))
                    .append('\n');
        }

        appendApplicationStack(sb, throwable);
    }

    private void appendApplicationStack(
            StringBuilder sb,
            IThrowableProxy throwable) {

        StackTraceElementProxy[] frames =
                throwable.getStackTraceElementProxyArray();

        int count = 0;

        for (StackTraceElementProxy frame : frames) {

            StackTraceElement element =
                    frame.getStackTraceElement();

            String className = element.getClassName();

            if (!className.startsWith(APPLICATION_PACKAGE)) {
                continue;
            }

            if (count == 0) {
                sb.append("\n<b>Details:</b>\n")
                        .append("<pre>");
            }

            sb.append("• ")
                    .append(escapeHtml(
                            getSimpleClassName(className)
                    ))
                    .append('.')
                    .append(escapeHtml(element.getMethodName()))
                    .append('(')
                    .append(escapeHtml(element.getFileName()))
                    .append(':')
                    .append(element.getLineNumber())
                    .append(")\n");

            count++;

            if (count >= MAX_STACK_FRAMES) {
                sb.append("</pre>");
                break;
            }
        }

        // Close <pre> if there were application frames
        if (count > 0 && count < MAX_STACK_FRAMES) {
            sb.append("</pre>");
        }
    }

    private static IThrowableProxy getRootCause(
            IThrowableProxy throwable) {

        if (throwable == null) {
            return null;
        }

        IThrowableProxy root = throwable;

        while (root.getCause() != null) {
            root = root.getCause();
        }

        return root;
    }

    private static String getSimpleClassName(String className) {

        int lastDot = className.lastIndexOf('.');

        if (lastDot < 0) {
            return className;
        }

        return className.substring(lastDot + 1);
    }

    private static String escapeHtml(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private static String truncate(String s) {

        if (s.length() <= MAX_LEN) {
            return s;
        }

        return s.substring(0, MAX_LEN)
                + "\n\n<i>… truncated — see server logs</i>";
    }
}