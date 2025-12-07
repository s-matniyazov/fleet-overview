package fleetoverview.controller;

import fleetoverview.service.NotificationService;
import fleetoverview.service.impl.TextNotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.NOTIFICATION;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 15 окт. 2025
 **/
@RestController
@RequestMapping(NOTIFICATION)
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(TextNotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping("test")
    public HttpEntity<?> test() {
        notificationService.sendNotifications();
        return ResponseEntity.ok("Sent");
    }
}
