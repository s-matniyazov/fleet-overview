package fleetoverview.service.impl;

import fleetoverview.config.MailConfigurationParams;
import fleetoverview.config.TelegramConfigurationParams;
import fleetoverview.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSocialServiceImpl implements SocialService {
    private final JavaMailSender mailSender;
    private final MailConfigurationParams mailParams;

    @Autowired
    public MailSocialServiceImpl(JavaMailSender mailSender, MailConfigurationParams mailParams) {
        this.mailSender = mailSender;
        this.mailParams = mailParams;
    }

    @Override
    public void sendMessage(String msg) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("bilol@efficientmanllc.com");
        helper.setSubject("⚠️ Fleet Alert");
        helper.setText(msg);

        if (mailParams.getSenders().isEmpty()) return;

        for (String mail : mailParams.getSenders().split(",")) {
            helper.setTo(mail);
            mailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendDocument(File document) throws FileNotFoundException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("bilol@efficientmanllc.com");
        helper.setSubject("⚠️ Fleet Alert");
        helper.setText("Report");

        // Excel file ni bog‘lash
        File file = new File("students.xlsx");
        helper.addAttachment(file.getName(), file);

        if (mailParams.getSenders().isEmpty()) return;

        for (String mail : mailParams.getSenders().split(",")) {
            helper.setTo(mail);
            mailSender.send(mimeMessage);
        }
    }
}
