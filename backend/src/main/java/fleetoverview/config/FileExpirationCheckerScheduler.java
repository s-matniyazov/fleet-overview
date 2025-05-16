package fleetoverview.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkFileAndSendNotification() {
        log.info("Mail service working time is  {}", dateFormat.format(new Date()));
    }
}
