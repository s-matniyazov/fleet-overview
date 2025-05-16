package fleetoverview.service.impl;

import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.domain.entity.TruckFileEntity;
import fleetoverview.domain.enums.TruckFileStatusEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.NotificationRepository;
import fleetoverview.repository.TruckFileRepository;
import fleetoverview.repository.TruckRepository;
import fleetoverview.service.NotificationService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;
    private final CompanyRepository companyRepository;
    private final TruckRepository truckRepository;
    private final TruckFileRepository truckFileRepository;

    public NotificationServiceImpl(NotificationRepository repository, TruckRepository truckRepository, CompanyRepository companyRepository, TruckFileRepository truckFileRepository) {
        this.repository = repository;
        this.truckRepository = truckRepository;
        this.companyRepository = companyRepository;
        this.truckFileRepository = truckFileRepository;
    }

    @Override
    public void sendNotifications() {
        var now = System.currentTimeMillis();
        var fiveDays = 5 * 1000 * 86400L;
        var companies = companyRepository.findAll();

        companies.forEach(it -> {
            StringBuilder text = new StringBuilder(String.format("""
                    Subject: 🔔 Compliance Alert: Upcoming Expirations & Missing Documents for %s
                                        
                                        
                    Dear Qobil,
                                        
                    This is an automated compliance notification from your [Portal Name] system regarding [Company Name].
                                        
                    Please review the following compliance alerts:
                       
                    🚛 Truck Documents Expiring Soon
                    Unit #102 (2020 Freightliner Cascadia)
                                        
                    Annual DOT Inspection – Expires on 2025-06-12
                                        
                    Registration – Expires on 2025-06-30
                                        
                    Unit #207 (2019 Volvo VNL)
                                        
                    IFTA Decal – Expires on 2025-06-15
                                        
                    ❌ Missing Truck Documents
                    Unit #305 (2021 Kenworth T680)
                                        
                    Proof of Insurance
                    """, it.getName()));
            var files = truckFileRepository.findAllByTruck_CompanyAndExpirationDateLessThanAndStatus(
                    it,
                    new Date(now + fiveDays),
                    TruckFileStatusEnum.ACTIVE
            );

            // nearly expired
            files.stream()
                    .filter(file -> file.getExpirationDate().before(new Date(now + fiveDays)) && file.getExpirationDate().after(new Date(now)))
                    .forEach(file -> {

                    });
        });
    }
}
