package fleetoverview.service.impl;

import fleetoverview.config.MailConfigurationParams;
import fleetoverview.config.TelegramConfigurationParams;
import fleetoverview.domain.enums.company.CompanyFileTypeEnum;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.DriverRepository;
import fleetoverview.repository.TrailerRepository;
import fleetoverview.repository.TruckRepository;
import fleetoverview.service.NotificationService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static fleetoverview.domain.enums.company.CompanyFileTypeEnum.*;
import static fleetoverview.domain.enums.driver.DriverFileTypeEnum.*;
import static fleetoverview.domain.enums.truck.TruckFileTypeEnum.*;
import static fleetoverview.util.helper.Utils.isNull;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
@Service
public class ExcelNotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(ExcelNotificationServiceImpl.class);
    private final CompanyRepository companyRepository;
    private final TruckRepository truckRepository;
    private final TrailerRepository trailerRepository;
    private final DriverRepository driverRepository;
    private final JavaMailSender mailSender;
    private final TelegramConfigurationParams telegramParams;
    private final MailConfigurationParams mailParams;

    @Autowired
    public ExcelNotificationServiceImpl(TruckRepository truckRepository,
                                        CompanyRepository companyRepository, TrailerRepository trailerRepository,
                                        JavaMailSender mailSender, TelegramConfigurationParams telegramParams,
                                        MailConfigurationParams mailParams, DriverRepository driverRepository) {
        this.truckRepository = truckRepository;
        this.companyRepository = companyRepository;
        this.trailerRepository = trailerRepository;
        this.mailSender = mailSender;
        this.telegramParams = telegramParams;
        this.mailParams = mailParams;
        this.driverRepository = driverRepository;
    }

    @Override
    public void sendNotifications() {
        var companies = companyRepository.findAllByStatus(CompanyStatusEnum.ACTIVE);

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("sheet");

        AtomicInteger rowIndex = new AtomicInteger(0);

        Row row = sheet.createRow(rowIndex.getAndIncrement());
        addCell(row, 0, "Company name");
        addCell(row, 1, "Type");
        addCell(row, 2, "Name");
        addCell(row, 3, "File");
        addCell(row, 4, "Info");

        companies.forEach(it -> {
            Row companyNameRow = sheet.createRow(rowIndex.getAndIncrement());
            addCell(companyNameRow, 0, it.getName());

            companyNotificationBuilder(sheet, rowIndex, it.getId());
        });

        try {
            FileOutputStream fileOut = new FileOutputStream("students.xlsx");
            workbook.write(fileOut);

            fileOut.close();

            workbook.close();
        } catch (IOException e) {
            logger.error("Error in closing sheet {}", e.getMessage());
        }
    }

    private void companyNotificationBuilder(Sheet sheet, AtomicInteger rowIndex, int companyId) {
        var companyFiles = companyRepository.getCompaniesWithExpirationInfo(companyId);

        companyFiles.forEach(cFile -> {
            Row row1 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row1, 3, INS_CERT.toString());
            addCell(row1, 4, dateToString(cFile.getInsuranceCertExp()));

            Row row2 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row2, 3, IFTA_LICENSE.toString());
            addCell(row2, 4, dateToString(cFile.getIftaExp()));

            Row row3 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row3, 3, UCR.toString());
            addCell(row3, 4, dateToString(cFile.getUcrExp()));

            Row row4 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row4, 3, CT_PERMIT.toString());
            addCell(row4, 4, dateToString(cFile.getPermitExp()));

            Row row5 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row5, 3, MCS_150.toString());
            addCell(row5, 4, dateToString(cFile.getMcsExp()));
        });
    }

    private String dateToString(LocalDate date) {
        if (date == null) return "none";
        return date.toString();
    }

    private void addCell(Row row, int cellIndex, String cellValue) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellValue);
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

        for (String chatId : telegramParams.getChatIds().split(",")) {
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

            for (String mail : mailParams.getSenders().split(",")) {
                helper.setTo(mail);
                mailSender.send(mimeMessage);
            }

        } catch (Exception e) {
            logger.error("MAIL_RESULT " + e.getMessage());
        }
    }

    private String truckNotificationBuilder(int companyId) {
        StringBuilder text = new StringBuilder();
        AtomicInteger counter = new AtomicInteger();

        var trucks = truckRepository.getTrucksWithExpirationInfo(companyId);
        text.append("--🚛-------------------------------------\n🕒 Truck Documents Expiring Soon\n");
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

                    counter.getAndIncrement();
                    text.append("\n");
                });
        if (counter.get() == 0) {
            text.append("\uD83D\uDFE2");
            text.append("\n");
        }
        counter.set(0);

        text.append("\n\uD83D\uDEAB Missing Truck Documents\n");
        trucks.stream()
                .filter(tr -> isNull(tr.getRegCabCardExp()) || isNull(tr.getAnnsInsExp())
                        || isNull(tr.getPhysDamageExp()) || isNull(tr.getLeaseAgrExp()))
                .forEach(tr -> {
                    text.append(String.format("#%s (%s %s - %s)\n", tr.getUnit(), tr.getMaker(), tr.getFuelType(), tr.getYear()));

                    if (isNull(tr.getRegCabCardExp())) text.append(missingText(REG_CAB_CARD.getDescription()));
                    if (isNull(tr.getAnnsInsExp())) text.append(missingText(ANN_INS.getDescription()));
                    if (isNull(tr.getPhysDamageExp())) text.append(missingText(PHYS_DAMAGE.getDescription()));
                    if (isNull(tr.getLeaseAgrExp())) text.append(missingText(LEASE_AGR.getDescription()));

                    counter.getAndIncrement();
                    text.append("\n");
                });
        if (counter.get() == 0) {
            text.append("\uD83D\uDFE2");
            text.append("\n");
        }

        return text.toString();
    }

    private String trailerNotificationBuilder(int companyId) {
        StringBuilder text = new StringBuilder();
        AtomicInteger counter = new AtomicInteger();

        var trailers = trailerRepository.getTrailersWithExpirationInfo(companyId);
        text.append("\n--🚃-------------------------------------\n🕒 Trailer Documents Expiring Soon\n");
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

                    counter.getAndIncrement();
                    text.append("\n");
                });
        if (counter.get() == 0) {
            text.append("\uD83D\uDFE2");
            text.append("\n");
        }
        counter.set(0);

        text.append("\n\uD83D\uDEAB Missing Trailer Documents\n");
        trailers.stream()
                .filter(tr -> isNull(tr.getRegCabCardExp()) || isNull(tr.getAnnsInsExp())
                        || isNull(tr.getPhysDamageExp()) || isNull(tr.getLeaseAgrExp()))
                .forEach(tr -> {
                    text.append(String.format("#%s (%s - %s)\n", tr.getUnit(), tr.getMaker(), tr.getYear()));

                    if (isNull(tr.getRegCabCardExp())) text.append(missingText(REG_CAB_CARD.getDescription()));
                    if (isNull(tr.getAnnsInsExp())) text.append(missingText(ANN_INS.getDescription()));
                    if (isNull(tr.getPhysDamageExp())) text.append(missingText(PHYS_DAMAGE.getDescription()));
                    if (isNull(tr.getLeaseAgrExp())) text.append(missingText(LEASE_AGR.getDescription()));

                    counter.getAndIncrement();
                    text.append("\n");
                });
        if (counter.get() == 0) {
            text.append("\uD83D\uDFE2");
            text.append("\n");
        }

        return text.toString();
    }

    private String driverNotificationBuilder(int companyId) {
        StringBuilder text = new StringBuilder();
        AtomicInteger counter = new AtomicInteger();

        var drivers = driverRepository.getDriversWithExpirationInfo(companyId);
        text.append("\n--\uD83E\uDD35\u200D♂\uFE0F-------------------------------------\n🕒 Driver Documents Expiring Soon\n");
        drivers.stream()
                .filter(tr -> isNearlyExpires(tr.getCdlExp()) || isNearlyExpires(tr.getMedicalCertExp())
                        || isNearlyExpires(tr.getMvrExp()) || isNearlyExpires(tr.getClearingHouseExp())
                        || isNearlyExpires(tr.getSsnExp()) || isNearlyExpires(tr.getCcfExp())
                        || isNearlyExpires(tr.getDriverApplicationExp())
                        || isNearlyExpires(tr.getPevExp())
                )
                .forEach(tr -> {
                    text.append(String.format("%s\n", tr.getDriverName()));

                    if (isNearlyExpires(tr.getCdlExp()))
                        text.append(expiresOnText(CDL.getDescription(), tr.getCdlExp()));
                    if (isNearlyExpires(tr.getMedicalCertExp()))
                        text.append(expiresOnText(MEDICAL_CERT.getDescription(), tr.getMedicalCertExp()));
                    if (isNearlyExpires(tr.getMvrExp()))
                        text.append(expiresOnText(MVR.getDescription(), tr.getMvrExp()));
                    if (isNearlyExpires(tr.getClearingHouseExp()))
                        text.append(expiresOnText(CLEARING_HOUSE.getDescription(), tr.getClearingHouseExp()));
                    if (isNearlyExpires(tr.getSsnExp()))
                        text.append(expiresOnText(SSN.getDescription(), tr.getSsnExp()));

                    if (isNearlyExpires(tr.getCcfExp()))
                        text.append(expiresOnText(CCF.getDescription(), tr.getCcfExp()));
                    if (isNearlyExpires(tr.getDriverApplicationExp()))
                        text.append(expiresOnText(DRIVER_APPLICATION.getDescription(), tr.getDriverApplicationExp()));
                    if (isNearlyExpires(tr.getPevExp()))
                        text.append(expiresOnText(PEV.getDescription(), tr.getPevExp()));

                    counter.getAndIncrement();
                    text.append("\n");
                });
        if (counter.get() == 0) {
            text.append("\uD83D\uDFE2");
            text.append("\n");
        }
        counter.set(0);

        text.append("\n\uD83D\uDEAB Missing Driver Documents\n");
        drivers.stream()
                .filter(tr -> isNull(tr.getCdlExp()) || isNull(tr.getMedicalCertExp())
                        || isNull(tr.getMvrExp()) || isNull(tr.getClearingHouseExp())
                        || isNull(tr.getSsnExp()) || isNull(tr.getCcfExp())
                        || isNull(tr.getDriverApplicationExp())
                        || isNull(tr.getPevExp()))
                .forEach(tr -> {
                    text.append(String.format("%s\n", tr.getDriverName()));

                    if (isNull(tr.getCdlExp())) text.append(missingText(CDL.getDescription()));
                    if (isNull(tr.getMedicalCertExp())) text.append(missingText(MEDICAL_CERT.getDescription()));
                    if (isNull(tr.getMvrExp())) text.append(missingText(MVR.getDescription()));
                    if (isNull(tr.getClearingHouseExp())) text.append(missingText(CLEARING_HOUSE.getDescription()));
                    if (isNull(tr.getSsnExp())) text.append(missingText(SSN.getDescription()));
                    if (isNull(tr.getCcfExp())) text.append(missingText(CCF.getDescription()));

                    if (isNull(tr.getDriverApplicationExp()))
                        text.append(missingText(DRIVER_APPLICATION.getDescription()));
                    if (isNull(tr.getPevExp())) text.append(missingText(PEV.getDescription()));

                    counter.getAndIncrement();
                    text.append("\n");
                });
        if (counter.get() == 0) {
            text.append("\uD83D\uDFE2");
            text.append("\n");
        }

        return text.toString();
    }
}
