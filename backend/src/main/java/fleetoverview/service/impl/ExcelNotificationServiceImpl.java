package fleetoverview.service.impl;

import fleetoverview.domain.entity.company.CompanyEntity;
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
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
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

        makeExcel(companies);

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

    private void makeExcel(List<CompanyEntity> companies) {
        Workbook workbook = new XSSFWorkbook();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);

        CellStyle borderedStyle = borderedStyle(workbook);
        borderedStyle.setAlignment(HorizontalAlignment.LEFT);
        CellStyle borderedBoldStyle = borderedStyle(workbook);
        borderedBoldStyle.setAlignment(HorizontalAlignment.CENTER);
        borderedBoldStyle.setFont(boldFont);

        CellStyle laStyle = borderedStyle(workbook);
        laStyle.setAlignment(HorizontalAlignment.LEFT);
        CellStyle raStyle = borderedStyle(workbook);
        raStyle.setAlignment(HorizontalAlignment.RIGHT);

        createCompanySheet(workbook, borderedStyle, borderedBoldStyle, laStyle, companies);
        createDriverSheet(workbook, borderedStyle, borderedBoldStyle, laStyle, companies);
        createTruckSheet(workbook, borderedStyle, borderedBoldStyle, laStyle, companies);
        createTrailerSheet(workbook, borderedStyle, borderedBoldStyle, laStyle, companies);

        try {
            FileOutputStream fileOut = new FileOutputStream("report.xlsx");
            workbook.write(fileOut);

            fileOut.close();

            workbook.close();
        } catch (IOException e) {
            logger.error("Error in closing sheet {}", e.getMessage());
        }
    }

    private void createCompanySheet(Workbook workbook, CellStyle borderedStyle, CellStyle borderedBoldStyle, CellStyle laStyle, List<CompanyEntity> companies) {
        Sheet sheet = workbook.createSheet("Company Files");

        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = sheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(borderedBoldStyle);
            addCell(companyNameRow, 1, "").setCellStyle(borderedStyle);
            addCell(companyNameRow, 2, it.getName()).setCellStyle(borderedBoldStyle);
            mergerRegion(sheet, companyNameRow.getRowNum(), 2);
            addCell(companyNameRow, 4, "").setCellStyle(borderedStyle);

            companyFileRowsBuilder(sheet, borderedStyle, laStyle, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createDriverSheet(Workbook workbook, CellStyle borderedStyle, CellStyle borderedBoldStyle, CellStyle laStyle, List<CompanyEntity> companies) {
        Sheet sheet = workbook.createSheet("Driver Files");

        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = sheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(borderedBoldStyle);
            addCell(companyNameRow, 1, "").setCellStyle(borderedStyle);
            addCell(companyNameRow, 2, it.getName()).setCellStyle(borderedBoldStyle);
            mergerRegion(sheet, companyNameRow.getRowNum(), 2);
            addCell(companyNameRow, 4, "").setCellStyle(borderedStyle);

            driverFileRowsBuilder(sheet, borderedStyle, laStyle, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createTruckSheet(Workbook workbook, CellStyle borderedStyle, CellStyle borderedBoldStyle, CellStyle laStyle, List<CompanyEntity> companies) {
        Sheet sheet = workbook.createSheet("Truck Files");

        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = sheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(borderedBoldStyle);
            addCell(companyNameRow, 1, "").setCellStyle(borderedStyle);
            addCell(companyNameRow, 2, it.getName()).setCellStyle(borderedBoldStyle);
            mergerRegion(sheet, companyNameRow.getRowNum(), 2);
            addCell(companyNameRow, 4, "").setCellStyle(borderedStyle);

            truckFileRowsBuilder(sheet, borderedStyle, laStyle, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createTrailerSheet(Workbook workbook, CellStyle borderedStyle, CellStyle borderedBoldStyle, CellStyle laStyle, List<CompanyEntity> companies) {
        Sheet sheet = workbook.createSheet("Trailer Files");

        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = sheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(borderedBoldStyle);
            addCell(companyNameRow, 1, "").setCellStyle(borderedStyle);
            addCell(companyNameRow, 2, it.getName()).setCellStyle(borderedBoldStyle);
            mergerRegion(sheet, companyNameRow.getRowNum(), 2);
            addCell(companyNameRow, 4, "").setCellStyle(borderedStyle);

            trailerFileRowsBuilder(sheet, borderedStyle, laStyle, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void companyFileRowsBuilder(Sheet sheet, CellStyle borderedStyle, CellStyle laStyle, AtomicInteger rowIndex, int companyId) {
        var companyFiles = companyRepository.getCompaniesWithExpirationInfo(companyId);

        createTableHeadRow(
                sheet.createRow(rowIndex.getAndIncrement()),
                laStyle,
                "No", "", "Item:", "Info:", "Notes:");

        companyFiles.forEach(cFile -> {
            int counter = 1;
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, "", INS_CERT.toString(), cFile.getInsuranceCertExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, "", IFTA_LICENSE.toString(), cFile.getIftaExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, "", UCR.toString(), cFile.getUcrExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, "", CT_PERMIT.toString(), cFile.getPermitExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter, "", MCS_150.toString(), cFile.getMcsExp());
        });
    }

    private void driverFileRowsBuilder(Sheet sheet, CellStyle borderedStyle, CellStyle laStyle, AtomicInteger rowIndex, int companyId) {
        var driverFiles = driverRepository.getDriversWithExpirationInfo(companyId);

        createTableHeadRow(
                sheet.createRow(rowIndex.getAndIncrement()),
                laStyle, "No", "Driver Name:", "Item:", "Info:", "Notes:");

        driverFiles.forEach(cFile -> {
            int counter = 1;
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, cFile.getDriverName(), CDL.toString(), cFile.getCdlExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, cFile.getDriverName(), MEDICAL_CERT.toString(), cFile.getMedicalCertExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, cFile.getDriverName(), MVR.toString(), cFile.getMvrExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, cFile.getDriverName(), CLEARING_HOUSE.toString(), cFile.getClearingHouseExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, cFile.getDriverName(), SSN.toString(), cFile.getSsnExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, cFile.getDriverName(), DRIVER_APPLICATION.toString(), cFile.getDriverApplicationExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter, cFile.getDriverName(), PEV.toString(), cFile.getPevExp());
        });
    }

    private void truckFileRowsBuilder(Sheet sheet, CellStyle borderedStyle, CellStyle laStyle, AtomicInteger rowIndex, int companyId) {
        var truckFiles = truckRepository.getTrucksWithExpirationInfo(companyId);

        createTableHeadRow(
                sheet.createRow(rowIndex.getAndIncrement()),
                laStyle, "No", "Truck Name:", "Item:", "Info:", "Notes:");

        truckFiles.forEach(cFile -> {
            int counter = 1;
            String name = String.format("#%s (%s %s - %s)\n", cFile.getUnit(), cFile.getMaker(), cFile.getFuelType(), cFile.getYear());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, name, REG_CAB_CARD.toString(), cFile.getRegCabCardExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, name, ANN_INS.toString(), cFile.getAnnsInsExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, name, PHYS_DAMAGE.toString(), cFile.getPhysDamageExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter, name, LEASE_AGR.toString(), cFile.getLeaseAgrExp());
        });
    }

    private void trailerFileRowsBuilder(Sheet sheet, CellStyle borderedStyle, CellStyle laStyle, AtomicInteger rowIndex, int companyId) {
        var trailerFiles = trailerRepository.getTrailersWithExpirationInfo(companyId);

        createTableHeadRow(
                sheet.createRow(rowIndex.getAndIncrement()),
                laStyle, "No", "Trailer Name:", "Item:", "Info:", "Notes:");

        trailerFiles.forEach(cFile -> {
            int counter = 1;
            String name = String.format("#%s (%s - %s)\n", cFile.getUnit(), cFile.getMaker(), cFile.getYear());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, name, REG_CAB_CARD.toString(), cFile.getRegCabCardExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, name, ANN_INS.toString(), cFile.getAnnsInsExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter++, name, PHYS_DAMAGE.toString(), cFile.getPhysDamageExp());
            createFileTypeRow(sheet.createRow(rowIndex.getAndIncrement()), borderedStyle, counter, name, LEASE_AGR.toString(), cFile.getLeaseAgrExp());
        });
    }

    private void createTableHeadRow(Row row, CellStyle laStyle, String... heads) {
        for (int i = 0; i < heads.length; i++) {
            addCell(row, i, heads[i]).setCellStyle(laStyle);
        }
    }

    private void createFileTypeRow(Row row, CellStyle borderedStyle, int number, String name, String fileType, LocalDate expiration) {
        if (isNearlyExpires(expiration) || isNull(expiration)) {
            addCell(row, 0, number).setCellStyle(borderedStyle);
            addCell(row, 1, name).setCellStyle(borderedStyle);
            addCell(row, 2, fileType).setCellStyle(borderedStyle);
            addCell(row, 3, dateToString(expiration)).setCellStyle(borderedStyle);
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

    private void mergerRegion(Sheet sheet, int rowIndex, int fromCell) {
        CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex, fromCell, 3);
        sheet.addMergedRegion(region);

        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
    }

    private String dateToString(LocalDate date) {
        if (date == null) return "Missing";
        return date.toString();
    }

    private Cell addCell(Row row, int cellIndex, String cellValue) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellValue);
        return cell;
    }

    private Cell addCell(Row row, int cellIndex, int cellValue) {
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
