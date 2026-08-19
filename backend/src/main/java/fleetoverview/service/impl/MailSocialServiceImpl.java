package fleetoverview.service.impl;

import fleetoverview.config.MailConfigurationProperties;
import fleetoverview.service.SocialService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

@Service
public class MailSocialServiceImpl implements SocialService {
    private final JavaMailSender mailSender;
    private final MailConfigurationProperties mailProperties;

    @Autowired
    public MailSocialServiceImpl(JavaMailSender mailSender, MailConfigurationProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public void sendMessage(String msg) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(mailProperties.getUsername());
        helper.setSubject("⚠️ Fleet Alert");
        helper.setText(msg);

        if (mailProperties.getReceiver().isEmpty()) return;

        for (String mail : mailProperties.getReceiver().split(",")) {
            helper.setTo(mail);
            mailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendDocument(File file) throws FileNotFoundException, MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(mailProperties.getUsername());
        helper.setSubject("⚠️ Fleet Alert");
        helper.setText("Report");

        // Excel file ni bog‘lash
        helper.addAttachment(file.getName(), file);

        if (mailProperties.getReceiver().isEmpty()) return;

        for (String mail : mailProperties.getReceiver().split(",")) {
            helper.setTo(mail);
            mailSender.send(mimeMessage);
        }
    }
}
