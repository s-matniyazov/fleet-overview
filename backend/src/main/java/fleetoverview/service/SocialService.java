package fleetoverview.service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;

public interface SocialService {
    void sendMessage(String msg) throws MessagingException;
    void sendDocument(File document) throws FileNotFoundException, MessagingException;
}
