package fleetoverview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mail")
@Data
public class MailConfigurationProperties {
    private String receiver;
    private String host;
    private String username;
    private String password;
    private int port;
}
