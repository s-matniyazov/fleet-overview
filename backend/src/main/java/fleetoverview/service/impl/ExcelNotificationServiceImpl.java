package fleetoverview.service.impl;

import fleetoverview.config.MailConfigurationParams;
import fleetoverview.config.TelegramConfigurationParams;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.DriverRepository;
import fleetoverview.repository.TrailerRepository;
import fleetoverview.repository.TruckRepository;
import fleetoverview.service.NotificationService;
import fleetoverview.service.SocialService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
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

    private final SocialService telegramService;
    private final SocialService mailService;

    @Autowired
    public ExcelNotificationServiceImpl(TruckRepository truckRepository, CompanyRepository companyRepository,
                                        TrailerRepository trailerRepository, DriverRepository driverRepository,
                                        TelegramSocialServiceImpl telegramService, MailSocialServiceImpl mailService) {
        this.truckRepository = truckRepository;
        this.companyRepository = companyRepository;
        this.trailerRepository = trailerRepository;
        this.driverRepository = driverRepository;
        this.telegramService = telegramService;
        this.mailService = mailService;
    }

    @Override
    public void sendNotifications() {
        var companies = companyRepository.findAllByStatus(CompanyStatusEnum.ACTIVE);

        Workbook workbook = new XSSFWorkbook();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);
        CellStyle borderedStyle = borderedStyle(workbook);
        CellStyle borderedGreyStyle = borderedStyle(workbook);
        borderedGreyStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        borderedGreyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle fileSectionStyle = borderedStyle(workbook);
        fileSectionStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        fileSectionStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        fileSectionStyle.setAlignment(HorizontalAlignment.CENTER);
        fileSectionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        fileSectionStyle.setFont(boldFont);

        Sheet sheet = workbook.createSheet("sheet");

        sheet.setColumnWidth(0, 8000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 3000);

        AtomicInteger rowIndex = new AtomicInteger(0);

        Row row = sheet.createRow(rowIndex.getAndIncrement());
        addCell(row, 0, "Company name").setCellStyle(borderedGreyStyle);
        addCell(row, 1, "Name").setCellStyle(borderedGreyStyle);
        addCell(row, 2, "File").setCellStyle(borderedGreyStyle);
        addCell(row, 3, "Info").setCellStyle(borderedGreyStyle);

        companies.forEach(it -> {
            Row companyNameRow = sheet.createRow(rowIndex.getAndIncrement());
            mergerRegion(sheet, companyNameRow.getRowNum(), 0, 3);

            addCell(companyNameRow, 0, it.getName()).setCellStyle(borderedGreyStyle);

            companyNotificationBuilder(sheet, rowIndex, it.getId());
            driverNotificationBuilder(sheet, fileSectionStyle, rowIndex, it.getId());
            truckNotificationBuilder(sheet, fileSectionStyle, rowIndex, it.getId());
            trailerNotificationBuilder(sheet, fileSectionStyle, rowIndex, it.getId());
        });

        try {
            FileOutputStream fileOut = new FileOutputStream("report.xlsx");
            workbook.write(fileOut);

            fileOut.close();

            workbook.close();
        } catch (IOException e) {
            logger.error("Error in closing sheet {}", e.getMessage());
        }

        try {
            telegramService.sendDocument(new File("report.xlsx"));
        } catch (FileNotFoundException | MessagingException e) {
            logger.error("Error on sending telegram {}", e.getMessage());
        }

        try {
            mailService.sendDocument(new File("report.xlsx"));
        } catch (FileNotFoundException | MessagingException e) {
            logger.error("Error on sending mail {}", e.getMessage());
        }
    }

    private void companyNotificationBuilder(Sheet sheet, AtomicInteger rowIndex, int companyId) {
        var companyFiles = companyRepository.getCompaniesWithExpirationInfo(companyId);

        companyFiles.forEach(cFile -> {
            Row row1 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row1, 2, INS_CERT.toString());
            addCell(row1, 3, dateToString(cFile.getInsuranceCertExp()));

            Row row2 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row2, 2, IFTA_LICENSE.toString());
            addCell(row2, 3, dateToString(cFile.getIftaExp()));

            Row row3 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row3, 2, UCR.toString());
            addCell(row3, 3, dateToString(cFile.getUcrExp()));

            Row row4 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row4, 2, CT_PERMIT.toString());
            addCell(row4, 3, dateToString(cFile.getPermitExp()));

            Row row5 = sheet.createRow(rowIndex.getAndIncrement());
            addCell(row5, 2, MCS_150.toString());
            addCell(row5, 3, dateToString(cFile.getMcsExp()));
        });
    }

    private void driverNotificationBuilder(Sheet sheet, CellStyle style, AtomicInteger rowIndex, int companyId) {
        var driverFiles = driverRepository.getDriversWithExpirationInfo(companyId);
        if (driverFiles.isEmpty()) return;

        Row driverFilesRow = sheet.createRow(rowIndex.getAndIncrement());
        mergerRegion(sheet, driverFilesRow.getRowNum(), 1, 3);
        addCell(driverFilesRow, 1, "Driver Files").setCellStyle(style);

        driverFiles.forEach(cFile -> {
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), CDL.toString(), cFile.getCdlExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), MEDICAL_CERT.toString(), cFile.getMedicalCertExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), MVR.toString(), cFile.getMvrExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), CLEARING_HOUSE.toString(), cFile.getClearingHouseExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), SSN.toString(), cFile.getSsnExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), DRIVER_APPLICATION.toString(), cFile.getDriverApplicationExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), cFile.getDriverName(), PEV.toString(), cFile.getPevExp());
        });
    }

    private void truckNotificationBuilder(Sheet sheet, CellStyle style, AtomicInteger rowIndex, int companyId) {
        var truckFiles = truckRepository.getTrucksWithExpirationInfo(companyId);
        if (truckFiles.isEmpty()) return;

        Row truckFilesRow = sheet.createRow(rowIndex.getAndIncrement());
        mergerRegion(sheet, truckFilesRow.getRowNum(), 1, 3);
        addCell(truckFilesRow, 1, "Truck Files").setCellStyle(style);

        truckFiles.forEach(cFile -> {
            String name = String.format("#%s (%s %s - %s)\n", cFile.getUnit(), cFile.getMaker(), cFile.getFuelType(), cFile.getYear());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, REG_CAB_CARD.toString(), cFile.getRegCabCardExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, ANN_INS.toString(), cFile.getAnnsInsExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, PHYS_DAMAGE.toString(), cFile.getPhysDamageExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, LEASE_AGR.toString(), cFile.getLeaseAgrExp());
        });
    }

    private void trailerNotificationBuilder(Sheet sheet, CellStyle style, AtomicInteger rowIndex, int companyId) {
        var trailerFiles = trailerRepository.getTrailersWithExpirationInfo(companyId);
        if (trailerFiles.isEmpty()) return;

        Row trailerFilesRow = sheet.createRow(rowIndex.getAndIncrement());
        mergerRegion(sheet, trailerFilesRow.getRowNum(), 1, 3);
        addCell(trailerFilesRow, 1, "Trailer Files").setCellStyle(style);

        trailerFiles.forEach(cFile -> {
            String name = String.format("#%s (%s - %s)\n", cFile.getUnit(), cFile.getMaker(), cFile.getYear());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, REG_CAB_CARD.toString(), cFile.getRegCabCardExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, ANN_INS.toString(), cFile.getAnnsInsExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, PHYS_DAMAGE.toString(), cFile.getPhysDamageExp());
            createFileTypeRow(sheet, rowIndex.getAndIncrement(), name, LEASE_AGR.toString(), cFile.getLeaseAgrExp());
        });
    }

    private void createFileTypeRow(Sheet sheet, int rowIndex, String driverName, String fileType, LocalDate expiration) {
        if (isNearlyExpires(expiration) || isNull(expiration)) {
            Row row1 = sheet.createRow(rowIndex);
            addCell(row1, 1, driverName);
            addCell(row1, 2, fileType);
            addCell(row1, 3, dateToString(expiration));
        }
    }

    private CellStyle borderedStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    private void mergerRegion(Sheet sheet, int rowIndex, int fromCell, int toCell) {
        CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex, fromCell, toCell);
        sheet.addMergedRegion(region);

        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
    }

    private String dateToString(LocalDate date) {
        if (date == null) return "none";
        return date.toString();
    }

    private Cell addCell(Row row, int cellIndex, String cellValue) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellValue);
        return cell;
    }

    private boolean isNearlyExpires(LocalDate date) {
        if (isNull(date)) return false;

        Calendar calendar = Calendar.getInstance();
        LocalDate yesterday = LocalDate.ofYearDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR) - 1);
        LocalDate sixDayAfterToday = LocalDate.ofYearDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR) + 6);

        return date.isAfter(yesterday) && date.isBefore(sixDayAfterToday);
    }
}
