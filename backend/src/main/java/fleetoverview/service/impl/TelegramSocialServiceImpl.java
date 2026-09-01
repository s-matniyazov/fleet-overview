package fleetoverview.service.impl;

import fleetoverview.config.TelegramConfigurationParams;
import fleetoverview.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramSocialServiceImpl implements SocialService {
    private final TelegramConfigurationParams telegramParams;

    @Autowired
    public TelegramSocialServiceImpl(TelegramConfigurationParams telegramParams) {
        this.telegramParams = telegramParams;
    }

    @Override
    public void sendMessage(String msg) {
        RestTemplate restTemplate = new RestTemplate();

        // JSON body
        Map<String, String> body = new HashMap<>();
        body.put("text", msg);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        if (telegramParams.getChatIds().isEmpty()) return;

        for (String chatId : telegramParams.getChatIds().split(",")) {
            body.put("chat_id", chatId);
            restTemplate.postForEntity(
                    String.format("https://api.telegram.org/bot%s/sendmessage", telegramParams.getToken()),
                    request,
                    String.class
            );
        }
    }

    @Override
    public void sendDocument(File document) throws FileNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        if (telegramParams.getChatIds().isEmpty()) return;


        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("caption", "Report - " + java.util.Date.from(Instant.now()));
        for (String chatId : telegramParams.getChatIds().split(",")) {
            body.add("chat_id", chatId);

            InputStreamResource resource = new InputStreamResource(new FileInputStream(document)) {
                @Override
                public String getFilename() {
                    return document.getName();
                }

                @Override
                public long contentLength() {
                    return document.length();
                }
            };

            body.add("document", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            restTemplate.postForEntity(
                    String.format("https://api.telegram.org/bot%s/sendDocument", telegramParams.getToken()),
                    request,
                    String.class
            );
        }
    }
}
