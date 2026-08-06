package fleetoverview.service.impl;

import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import fleetoverview.repository.*;
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

import jakarta.mail.MessagingException;
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
import static fleetoverview.domain.enums.inspection.InspectionFileTypeEnum.CERTIFICATION;
import static fleetoverview.domain.enums.inspection.InspectionFileTypeEnum.CORRECTION;
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
    private final InspectionRepository inspectionRepository;

    @Autowired
    public ExcelNotificationServiceImpl(TruckRepository truckRepository, CompanyRepository companyRepository,
                                        TrailerRepository trailerRepository, DriverRepository driverRepository,
                                        TelegramSocialServiceImpl telegramService, MailSocialServiceImpl mailService, InspectionRepository inspectionRepository) {
        this.truckRepository = truckRepository;
        this.companyRepository = companyRepository;
        this.trailerRepository = trailerRepository;
        this.driverRepository = driverRepository;
        this.telegramService = telegramService;
        this.mailService = mailService;
        this.inspectionRepository = inspectionRepository;
    }

    @Override
    public void sendNotifications() {
        logger.info("excel notification is running ");
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

        ExcelData excelData = new ExcelData(workbook);

        createCompanySheet(excelData, companies);
        createDriverSheet(excelData, companies);
        createTruckSheet(excelData, companies);
        createTrailerSheet(excelData, companies);
        createInspectionSheet(excelData, companies);

        try {
            FileOutputStream fileOut = new FileOutputStream("report.xlsx");
            workbook.write(fileOut);

            fileOut.close();

            workbook.close();
        } catch (IOException e) {
            logger.error("Error in closing sheet {}", e.getMessage());
        }
    }

    private void createCompanySheet(ExcelData excelData, List<CompanyEntity> companies) {
        excelData.setCurrentSheet("Company Files");

        excelData.currentSheet.setColumnWidth(1, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = excelData.currentSheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(excelData.borderedBoldStyle);
            addCell(companyNameRow, 1, it.getName()).setCellStyle(excelData.borderedBoldStyle);
            mergerRegion(excelData.currentSheet, companyNameRow.getRowNum(), 1, 3);

            companyFileRowsBuilder(excelData, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createDriverSheet(ExcelData excelData, List<CompanyEntity> companies) {
        excelData.setCurrentSheet("Driver Files");

        excelData.currentSheet.setColumnWidth(1, 6000);
        excelData.currentSheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = excelData.currentSheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(excelData.borderedBoldStyle);
            addCell(companyNameRow, 1, it.getName()).setCellStyle(excelData.borderedBoldStyle);
            mergerRegion(excelData.currentSheet, companyNameRow.getRowNum(), 1, 4);

            driverFileRowsBuilder(excelData, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createTruckSheet(ExcelData excelData, List<CompanyEntity> companies) {
        excelData.setCurrentSheet("Truck Files");

        excelData.currentSheet.setColumnWidth(1, 6000);
        excelData.currentSheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = excelData.currentSheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(excelData.borderedBoldStyle);
            addCell(companyNameRow, 1, it.getName()).setCellStyle(excelData.borderedBoldStyle);
            mergerRegion(excelData.currentSheet, companyNameRow.getRowNum(), 1, 4);

            truckFileRowsBuilder(excelData, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createTrailerSheet(ExcelData excelData, List<CompanyEntity> companies) {
        excelData.setCurrentSheet("Trailer Files");

        excelData.currentSheet.setColumnWidth(1, 6000);
        excelData.currentSheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = excelData.currentSheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(excelData.borderedBoldStyle);
            addCell(companyNameRow, 1, it.getName()).setCellStyle(excelData.borderedBoldStyle);
            mergerRegion(excelData.currentSheet, companyNameRow.getRowNum(), 1, 4);

            trailerFileRowsBuilder(excelData, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void createInspectionSheet(ExcelData excelData, List<CompanyEntity> companies) {
        excelData.setCurrentSheet("Inspection Files");

        excelData.currentSheet.setColumnWidth(1, 6000);
        excelData.currentSheet.setColumnWidth(2, 8000);

        AtomicInteger rowIndex = new AtomicInteger(0);
        AtomicInteger companyCounter = new AtomicInteger(0);

        companies.forEach(it -> {
            Row companyNameRow = excelData.currentSheet.createRow(rowIndex.getAndIncrement());

            addCell(companyNameRow, 0, String.valueOf(companyCounter.getAndIncrement())).setCellStyle(excelData.borderedBoldStyle);
            addCell(companyNameRow, 1, it.getName()).setCellStyle(excelData.borderedBoldStyle);
            mergerRegion(excelData.currentSheet, companyNameRow.getRowNum(), 1, 4);

            inspectionFileRowBuilder(excelData, rowIndex, it.getId());

            rowIndex.incrementAndGet();
            rowIndex.incrementAndGet();
        });
    }

    private void companyFileRowsBuilder(ExcelData excelData, AtomicInteger rowIndex, int companyId) {
        var companyFiles = companyRepository.getCompaniesWithExpirationInfo(companyId);

        if (companyFiles.isEmpty()) {
            createNoDataRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), 3, "No Company Files", excelData.borderedACenterStyle);
            return;
        }

        createTableHeadRow(
                excelData.currentSheet.createRow(rowIndex.getAndIncrement()),
                excelData.laStyle,
                "No",    "Item:", "Info:", "Notes:");

        companyFiles.forEach(cFile -> {
            int counter = 1;
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, "", INS_CERT.toString(), cFile.getInsuranceCertExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, "", IFTA_LICENSE.toString(), cFile.getIftaExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, "", UCR.toString(), cFile.getUcrExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, "", CT_PERMIT.toString(), cFile.getPermitExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter, "", MCS_150.toString(), cFile.getMcsExp());
        });
    }

    private void driverFileRowsBuilder(ExcelData excelData, AtomicInteger rowIndex, int companyId) {
        var driverFiles = driverRepository.getDriversWithExpirationInfo(companyId);

        if (driverFiles.isEmpty()) {
            createNoDataRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), 4, "No Driver Files", excelData.borderedACenterStyle);
            return;
        }

        createTableHeadRow(
                excelData.currentSheet.createRow(rowIndex.getAndIncrement()),
                excelData.laStyle, "No", "Driver Name:", "Item:", "Info:", "Notes:");

        driverFiles.forEach(cFile -> {
            int counter = 1;
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getDriverName(), CDL.toString(), cFile.getCdlExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getDriverName(), MEDICAL_CERT.toString(), cFile.getMedicalCertExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getDriverName(), MVR.toString(), cFile.getMvrExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getDriverName(), CLEARING_HOUSE.toString(), cFile.getClearingHouseExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getDriverName(), SSN.toString(), cFile.getSsnExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getDriverName(), DRIVER_APPLICATION.toString(), cFile.getDriverApplicationExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter, cFile.getDriverName(), PEV.toString(), cFile.getPevExp());
        });
    }

    private void truckFileRowsBuilder(ExcelData excelData, AtomicInteger rowIndex, int companyId) {
        var truckFiles = truckRepository.getTrucksWithExpirationInfo(companyId);

        if (truckFiles.isEmpty()) {
            createNoDataRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), 4, "No Truck Files", excelData.borderedACenterStyle);
            return;
        }

        createTableHeadRow(
                excelData.currentSheet.createRow(rowIndex.getAndIncrement()),
                excelData.laStyle, "No", "Truck Name:", "Item:", "Info:", "Notes:");

        truckFiles.forEach(cFile -> {
            int counter = 1;
            String name = String.format("#%s (%s %s - %s)\n", cFile.getUnit(), cFile.getMaker(), cFile.getFuelType(), cFile.getYear());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, name, REG_CAB_CARD.toString(), cFile.getRegCabCardExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, name, ANN_INS.toString(), cFile.getAnnsInsExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, name, PHYS_DAMAGE.toString(), cFile.getPhysDamageExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter, name, LEASE_AGR.toString(), cFile.getLeaseAgrExp());
        });
    }

    private void trailerFileRowsBuilder(ExcelData excelData, AtomicInteger rowIndex, int companyId) {
        var trailerFiles = trailerRepository.getTrailersWithExpirationInfo(companyId);

        if (trailerFiles.isEmpty()) {
            createNoDataRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), 4, "No Trailer Files", excelData.borderedACenterStyle);
            return;
        }

        createTableHeadRow(
                excelData.currentSheet.createRow(rowIndex.getAndIncrement()),
                excelData.laStyle, "No", "Trailer Name:", "Item:", "Info:", "Notes:");

        trailerFiles.forEach(cFile -> {
            int counter = 1;
            String name = String.format("#%s (%s - %s)\n", cFile.getUnit(), cFile.getMaker(), cFile.getYear());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, name, REG_CAB_CARD.toString(), cFile.getRegCabCardExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, name, ANN_INS.toString(), cFile.getAnnsInsExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, name, PHYS_DAMAGE.toString(), cFile.getPhysDamageExp());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter, name, LEASE_AGR.toString(), cFile.getLeaseAgrExp());
        });
    }

    private void inspectionFileRowBuilder(ExcelData excelData, AtomicInteger rowIndex, int companyId) {
        var inspections = inspectionRepository.findInspectionsWithDeadlineAndMissingFiles(companyId);

        if (inspections.isEmpty()) {
            createNoDataRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), 4, "No Inspection Files", excelData.borderedACenterStyle);
            return;
        }

        createTableHeadRow(
                excelData.currentSheet.createRow(rowIndex.getAndIncrement()),
                excelData.laStyle, "No", "Inspection Number:", "Item:", "Info:", "Notes:");

        inspections.forEach(cFile -> {
            int counter = 1;
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter++, cFile.getInspectionNumber(), CORRECTION.toString(), cFile.getDeadlineAt());
            createFileTypeRow(excelData.currentSheet.createRow(rowIndex.getAndIncrement()), excelData.borderedStyle, excelData.raStyle, counter, cFile.getInspectionNumber(), CERTIFICATION.toString(), cFile.getDeadlineAt());
        });
    }

    private void createTableHeadRow(Row row, CellStyle laStyle, String... heads) {
        for (int i = 0; i < heads.length; i++) {
            addCell(row, i, heads[i]).setCellStyle(laStyle);
        }
    }

    private void createNoDataRow(Row row, int toCellNum, String info, CellStyle borderedACenterStyle) {
        addCell(row, 0, info).setCellStyle(borderedACenterStyle);
        mergerRegion(row.getSheet(), row.getRowNum(), 0, toCellNum);
    }

    private void createFileTypeRow(Row row, CellStyle borderedStyle, CellStyle raStyle, int number, String name, String fileType, LocalDate expiration) {
        if (isNearlyExpires(expiration) || isNull(expiration)) {
            addCell(row, number).setCellStyle(raStyle);
            int cellNum = 1;
            if (!name.isEmpty()) addCell(row, cellNum++, name).setCellStyle(borderedStyle);
            addCell(row, cellNum++, fileType).setCellStyle(borderedStyle);
            addCell(row, cellNum++, dateToString(expiration)).setCellStyle(borderedStyle);
            addCell(row, cellNum, "").setCellStyle(borderedStyle);
        }
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
        if (date == null) return "Missing";
        return date.toString();
    }

    private Cell addCell(Row row, int cellIndex, String cellValue) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellValue);
        return cell;
    }

    private Cell addCell(Row row, int cellValue) {
        Cell cell = row.createCell(0);
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

    private static class ExcelData {
        private final Workbook workbook;

        private Sheet currentSheet;

        private final CellStyle borderedStyle;
        private final CellStyle borderedBoldStyle;
        private final CellStyle borderedACenterStyle;

        private final CellStyle laStyle;
        private final CellStyle raStyle;

        private ExcelData(Workbook workbook) {
            this.workbook = workbook;

            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            CellStyle boldStyle = workbook.createCellStyle();
            boldStyle.setFont(boldFont);

            borderedStyle = borderedStyle(workbook);
            borderedStyle.setAlignment(HorizontalAlignment.LEFT);

            borderedBoldStyle = borderedStyle(workbook);
            borderedBoldStyle.setAlignment(HorizontalAlignment.CENTER);
            borderedBoldStyle.setFont(boldFont);

            borderedACenterStyle = borderedStyle(workbook);
            borderedACenterStyle.setAlignment(HorizontalAlignment.CENTER);

            laStyle = borderedStyle(workbook);
            laStyle.setAlignment(HorizontalAlignment.LEFT);

            raStyle = borderedStyle(workbook);
            raStyle.setAlignment(HorizontalAlignment.RIGHT);
        }

        private void setCurrentSheet(String sheetName) {
            this.currentSheet = workbook.createSheet(sheetName);
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
    }
}
