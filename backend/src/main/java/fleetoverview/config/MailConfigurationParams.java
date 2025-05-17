package fleetoverview.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 17 май 2025
 **/
@Component
@ConfigurationProperties(prefix = "mail")
public class MailConfigurationParams {
    private String senders;

    public String getSenders() {
        return senders;
    }

    public void setSenders(String senders) {
        this.senders = senders;
    }
}
