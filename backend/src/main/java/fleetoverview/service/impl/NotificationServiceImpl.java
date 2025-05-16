package fleetoverview.service.impl;

import fleetoverview.domain.enums.TruckFileTypeEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.NotificationRepository;
import fleetoverview.repository.TruckFileRepository;
import fleetoverview.repository.TruckRepository;
import fleetoverview.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static fleetoverview.domain.enums.TruckFileTypeEnum.*;
import static fleetoverview.util.helper.Utils.isNull;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository repository;
    private final CompanyRepository companyRepository;
    private final TruckRepository truckRepository;
    private final TruckFileRepository truckFileRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public NotificationServiceImpl(NotificationRepository repository, TruckRepository truckRepository,
                                   CompanyRepository companyRepository, TruckFileRepository truckFileRepository,
                                   JavaMailSender mailSender) {
        this.repository = repository;
        this.truckRepository = truckRepository;
        this.companyRepository = companyRepository;
        this.truckFileRepository = truckFileRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotifications() {
        var companies = companyRepository.findAll();

        companies.forEach(it -> {
            StringBuilder text = new StringBuilder(String.format("""
                    Subject: 🔔 Compliance Alert: Upcoming Expirations & Missing Documents for %s
                                        
                                        
                    Dear Qobil,
                                        
                    This is an automated compliance notification from your Portal system regarding %s.
                                        
                    Please review the following compliance alerts:
                       \
                    """, it.getName(), it.getName()));

            var trucks = truckRepository.findTruck(it.getId());

            text.append("""
                    🚛 Truck Documents Expiring Soon
                    """);
            trucks.stream()
                    .filter(tr -> isNearlyExpires(tr.getRegCabCardExp()) || isNearlyExpires(tr.getAnnsInsExp())
                            || isNearlyExpires(tr.getPhysDamageExp()) || isNearlyExpires(tr.getLeaseAgrExp()))
                    .forEach(tr -> {
                        text.append(String.format("#%s (%s %s)\n", tr.getTruckUnit(), tr.getTruckMaker(), tr.getTruckFuelType()));

                        if (isNearlyExpires(tr.getRegCabCardExp()))
                            text.append(expiresOnText(REG_CAB_CARD, tr.getRegCabCardExp()));
                        if (isNearlyExpires(tr.getAnnsInsExp()))
                            text.append(expiresOnText(ANN_INS, tr.getAnnsInsExp()));
                        if (isNearlyExpires(tr.getPhysDamageExp()))
                            text.append(expiresOnText(PHYS_DAMAGE, tr.getPhysDamageExp()));
                        if (isNearlyExpires(tr.getLeaseAgrExp()))
                            text.append(expiresOnText(LEASE_AGR, tr.getLeaseAgrExp()));

                        text.append("\n");
                    });


            text.append("""
                    ❌ Missing Truck Documents
                    """);
            trucks.stream()
                    .filter(tr -> isNull(tr.getRegCabCardExp()) || isNull(tr.getAnnsInsExp())
                            || isNull(tr.getPhysDamageExp()) || isNull(tr.getLeaseAgrExp()))
                    .forEach(tr -> {
                        text.append(String.format("#%s (%s %s)\n", tr.getTruckUnit(), tr.getTruckMaker(), tr.getTruckFuelType()));

                        if (isNull(tr.getRegCabCardExp())) text.append(missingText(REG_CAB_CARD));
                        if (isNull(tr.getAnnsInsExp())) text.append(missingText(ANN_INS));
                        if (isNull(tr.getPhysDamageExp())) text.append(missingText(PHYS_DAMAGE));
                        if (isNull(tr.getLeaseAgrExp())) text.append(missingText(LEASE_AGR));

                        text.append("\n");
                    });

            sendToGmail(text.toString());
            sendToTelegram(text.toString());
        });
    }

    private boolean isNearlyExpires(LocalDate date) {
        if (isNull(date)) return false;

        Calendar calendar = Calendar.getInstance();
        LocalDate yesterday = LocalDate.ofYearDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR) - 1);
        LocalDate sixDayAfterToday = LocalDate.ofYearDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR) + 6);

        return date.isAfter(yesterday) && date.isBefore(sixDayAfterToday);
    }

    private boolean isExpires(LocalDate date) {
        if (isNull(date)) return false;

        LocalDate today = LocalDate.now();

        return date.isBefore(today);
    }

    private String expiresOnText(TruckFileTypeEnum type, LocalDate date) {
        return String.format("%s - %s\n", type.getDescription(), date);
    }

    private String missingText(TruckFileTypeEnum type) {
        return type.getDescription() + "\n";
    }

    private void sendToTelegram(String message) {
        RestTemplate restTemplate = new RestTemplate();

        // JSON body
        Map<String, String> body = new HashMap<>();
        body.put("chat_id", "1092527096");
        body.put("text", message);

        // HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // Send the request as POST
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.telegram.org/bot6174384781:AAENYCRflrXFt6KERjQC1LhW6yd_zs-7Vbc/sendmessage", request, String.class);

        // Optional: Print or log the response
        System.out.println("Telegram response: " + response.getBody());
//
//        try {
//            String fooResourceUrl = "https://api.telegram.org/bot6174384781:AAENYCRflrXFt6KERjQC1LhW6yd_zs-7Vbc/sendmessage?chat_id=1092527096&text=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
//
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getForObject(fooResourceUrl, String.class);
//
//        } catch (Exception e) {
//            logger.error("TG_RESULT " + e.getMessage());
//        }
    }

    private void sendToGmail(String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo("sardorbekmatniyazov03@gmail.com");
            helper.setSubject("fleet alert");
            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("MAIL_RESULT " + e.getMessage());
        }
    }
}
