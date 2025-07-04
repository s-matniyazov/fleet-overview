package fleetoverview.config;

import fleetoverview.service.FileCheckerService;
import fleetoverview.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 май 2025
 **/
@Component
public class FileExpirationCheckerScheduler {
    private static final Logger log = LoggerFactory.getLogger(FileExpirationCheckerScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final NotificationService notificationService;
    private final FileCheckerService truckFileCheckerServiceImpl;
    private final FileCheckerService permitFileCheckerServiceImpl;

    @Autowired
    public FileExpirationCheckerScheduler(NotificationService notificationService, FileCheckerService truckFileCheckerServiceImpl, FileCheckerService permitFileCheckerServiceImpl) {
        this.notificationService = notificationService;
        this.truckFileCheckerServiceImpl = truckFileCheckerServiceImpl;
        this.permitFileCheckerServiceImpl = permitFileCheckerServiceImpl;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void checkFileAndMakeExpired() {
        truckFileCheckerServiceImpl.checkAndDisableFile();
        permitFileCheckerServiceImpl.checkAndDisableFile();
    }

    //cron = "0 0 8 * * ?"
//    @Scheduled(cron = "0 0 8 * * ?"/*fixedRate = 60 * 60 * 1000*//* fixedDelay = 1000L*/)
    @Scheduled(fixedDelay = 1000L)
    public void checkFileAndSendNotification() {
        log.info("Mail service working time is  {}", dateFormat.format(new Date()));
        notificationService.sendNotifications();
    }
}
