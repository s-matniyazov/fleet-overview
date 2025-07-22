package fleetoverview.service.impl;

import fleetoverview.config.MailConfigurationParams;
import fleetoverview.config.TelegramConfigurationParams;
import fleetoverview.domain.enums.truck.TruckFileTypeEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.TrailerRepository;
import fleetoverview.repository.TruckRepository;
import fleetoverview.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static fleetoverview.domain.enums.truck.TruckFileTypeEnum.*;
import static fleetoverview.util.helper.Utils.isNull;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final CompanyRepository companyRepository;
    private final TruckRepository truckRepository;
    private final TrailerRepository trailerRepository;
    private final JavaMailSender mailSender;
    private final TelegramConfigurationParams telegramParams;
    private final MailConfigurationParams mailParams;

    @Autowired
    public NotificationServiceImpl(TruckRepository truckRepository,
                                   CompanyRepository companyRepository, TrailerRepository trailerRepository,
                                   JavaMailSender mailSender, TelegramConfigurationParams telegramParams, MailConfigurationParams mailParams) {
        this.truckRepository = truckRepository;
        this.companyRepository = companyRepository;
        this.trailerRepository = trailerRepository;
        this.mailSender = mailSender;
        this.telegramParams = telegramParams;
        this.mailParams = mailParams;
    }

    @Override
    public void sendNotifications() {
        var companies = companyRepository.findAll();

        companies.forEach(it -> {
            AtomicInteger counter = new AtomicInteger();

            StringBuilder text = new StringBuilder(String.format("""
                    Subject: 🔔 Compliance Alert: Upcoming Expirations & Missing Documents for %s
                                        
                                        
                    Dear Qobil,
                                        
                    This is an automated compliance notification from your Efficient management regarding %s.
                                        
                    Please review the following compliance alerts:
                    
                    """, it.getName(), it.getName()));

            var trucks = truckRepository.getTrucksWithExpirationInfo(it.getId());
            text.append("----------------------------------------\n🚛 Truck Documents Expiring Soon\n");
            trucks.stream()
                    .filter(tr -> isNearlyExpires(tr.getRegCabCardExp()) || isNearlyExpires(tr.getAnnsInsExp())
                            || isNearlyExpires(tr.getPhysDamageExp()) || isNearlyExpires(tr.getLeaseAgrExp()))
                    .forEach(tr -> {
                        text.append(String.format("#%s (%s %s - %s)\n", tr.getUnit(), tr.getMaker(), tr.getFuelType(), tr.getYear()));

                        if (isNearlyExpires(tr.getRegCabCardExp()))
                            text.append(expiresOnText(REG_CAB_CARD.getDescription(), tr.getRegCabCardExp()));
                        if (isNearlyExpires(tr.getAnnsInsExp()))
                            text.append(expiresOnText(ANN_INS.getDescription(), tr.getAnnsInsExp()));
                        if (isNearlyExpires(tr.getPhysDamageExp()))
                            text.append(expiresOnText(PHYS_DAMAGE.getDescription(), tr.getPhysDamageExp()));
                        if (isNearlyExpires(tr.getLeaseAgrExp()))
                            text.append(expiresOnText(LEASE_AGR.getDescription(), tr.getLeaseAgrExp()));

                        text.append("\n");

                        counter.getAndIncrement();
                    });


            text.append("\n❌ Missing Truck Documents\n");
            trucks.stream()
                    .filter(tr -> isNull(tr.getRegCabCardExp()) || isNull(tr.getAnnsInsExp())
                            || isNull(tr.getPhysDamageExp()) || isNull(tr.getLeaseAgrExp()))
                    .forEach(tr -> {
                        text.append(String.format("#%s (%s %s - %s)\n", tr.getUnit(), tr.getMaker(), tr.getFuelType(), tr.getYear()));

                        if (isNull(tr.getRegCabCardExp())) text.append(missingText(REG_CAB_CARD.getDescription()));
                        if (isNull(tr.getAnnsInsExp())) text.append(missingText(ANN_INS.getDescription()));
                        if (isNull(tr.getPhysDamageExp())) text.append(missingText(PHYS_DAMAGE.getDescription()));
                        if (isNull(tr.getLeaseAgrExp())) text.append(missingText(LEASE_AGR.getDescription()));

                        text.append("\n");

                        counter.getAndIncrement();
                    });

            var trailers = trailerRepository.getTrailersWithExpirationInfo(it.getId());
            text.append("----------------------------------------\n🚃 Trailer Documents Expiring Soon\n");
            trailers.stream()
                    .filter(tr -> isNearlyExpires(tr.getRegCabCardExp()) || isNearlyExpires(tr.getAnnsInsExp())
                            || isNearlyExpires(tr.getPhysDamageExp()) || isNearlyExpires(tr.getLeaseAgrExp()))
                    .forEach(tr -> {
                        text.append(String.format("#%s (%s - %s)\n", tr.getUnit(), tr.getMaker(), tr.getYear()));

                        if (isNearlyExpires(tr.getRegCabCardExp()))
                            text.append(expiresOnText(REG_CAB_CARD.getDescription(), tr.getRegCabCardExp()));
                        if (isNearlyExpires(tr.getAnnsInsExp()))
                            text.append(expiresOnText(ANN_INS.getDescription(), tr.getAnnsInsExp()));
                        if (isNearlyExpires(tr.getPhysDamageExp()))
                            text.append(expiresOnText(PHYS_DAMAGE.getDescription(), tr.getPhysDamageExp()));
                        if (isNearlyExpires(tr.getLeaseAgrExp()))
                            text.append(expiresOnText(LEASE_AGR.getDescription(), tr.getLeaseAgrExp()));

                        text.append("\n");

                        counter.getAndIncrement();
                    });


            text.append("\n❌ Missing Trailer Documents\n");
            trailers.stream()
                    .filter(tr -> isNull(tr.getRegCabCardExp()) || isNull(tr.getAnnsInsExp())
                            || isNull(tr.getPhysDamageExp()) || isNull(tr.getLeaseAgrExp()))
                    .forEach(tr -> {
                        text.append(String.format("#%s (%s - %s)\n", tr.getUnit(), tr.getMaker(), tr.getYear()));

                        if (isNull(tr.getRegCabCardExp())) text.append(missingText(REG_CAB_CARD.getDescription()));
                        if (isNull(tr.getAnnsInsExp())) text.append(missingText(ANN_INS.getDescription()));
                        if (isNull(tr.getPhysDamageExp())) text.append(missingText(PHYS_DAMAGE.getDescription()));
                        if (isNull(tr.getLeaseAgrExp())) text.append(missingText(LEASE_AGR.getDescription()));

                        text.append("\n");

                        counter.getAndIncrement();
                    });

            if (counter.get() != 0) {
                sendToGmail(text.toString());
                sendToTelegram(text.toString());
            }
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

    private String expiresOnText(String type, LocalDate date) {
        return String.format("%s - %s\n", type, date);
    }

    private String missingText(String type) {
        return type + "\n";
    }

    private void sendToTelegram(String message) {
        RestTemplate restTemplate = new RestTemplate();

        // JSON body
        Map<String, String> body = new HashMap<>();
        body.put("text", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        if (telegramParams.getChatIds().isEmpty()) return;

        for (String chatId: telegramParams.getChatIds().split(",")) {
            body.put("chat_id", chatId);
            restTemplate.postForEntity(
                    String.format("https://api.telegram.org/bot%s/sendmessage", telegramParams.getToken()),
                    request,
                    String.class
            );
        }
    }

    private void sendToGmail(String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("bilol@efficientmanllc.com");
            helper.setSubject("⚠️ Fleet Alert");
            helper.setText(message);

            if (mailParams.getSenders().isEmpty()) return;

            for (String mail: mailParams.getSenders().split(",")) {
                helper.setTo(mail);
                mailSender.send(mimeMessage);
            }

        } catch (Exception e) {
            logger.error("MAIL_RESULT " + e.getMessage());
        }
    }
}
