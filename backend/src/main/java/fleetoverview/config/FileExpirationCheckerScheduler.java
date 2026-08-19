package fleetoverview.config;

import fleetoverview.service.FileCheckerService;
import fleetoverview.service.NotificationService;
import fleetoverview.service.impl.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class FileExpirationCheckerScheduler {
    private static final Logger log = LoggerFactory.getLogger(FileExpirationCheckerScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final NotificationService notificationService;
    private final FileCheckerService truckFileCheckerServiceImpl;
    private final FileCheckerService permitFileCheckerServiceImpl;
    private final DriverFileCheckerServiceImpl driverFileCheckerService;
    private final CompanyFileCheckerServiceImpl companyFileCheckerService;
    private final InspectionServiceImpl inspectionServiceImpl;

    @Autowired
    public FileExpirationCheckerScheduler(ExcelNotificationServiceImpl notificationService, FileCheckerService truckFileCheckerServiceImpl,
                                          FileCheckerService permitFileCheckerServiceImpl, DriverFileCheckerServiceImpl driverFileCheckerService,
                                          CompanyFileCheckerServiceImpl companyFileCheckerService, InspectionServiceImpl inspectionServiceImpl) {
        this.notificationService = notificationService;
        this.truckFileCheckerServiceImpl = truckFileCheckerServiceImpl;
        this.permitFileCheckerServiceImpl = permitFileCheckerServiceImpl;
        this.driverFileCheckerService = driverFileCheckerService;
        this.companyFileCheckerService = companyFileCheckerService;
        this.inspectionServiceImpl = inspectionServiceImpl;

    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void checkFileAndMakeExpired() {
        truckFileCheckerServiceImpl.checkAndDisableFile();
        permitFileCheckerServiceImpl.checkAndDisableFile();
        driverFileCheckerService.checkAndDisableFile();
        companyFileCheckerService.checkAndDisableFile();
    }

    //cron = "0 0 8 * * ?"
//    @Scheduled(cron = "0 0 8 * * ?"/*fixedRate = 60 * 60 * 1000*//* fixedDelay = 1000L*/)
    @Scheduled(cron = "0 0 8 * * ?")
    public void checkFileAndSendNotification() {
        log.info("Mail service working time is  {}", dateFormat.format(new Date()));
        notificationService.sendNotifications();
    }
}
