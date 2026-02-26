package fleetoverview.data.request;

import fleetoverview.domain.enums.driver.DriverFileClassEnum;
import fleetoverview.domain.enums.driver.DriverFileTypeEnum;

import java.time.LocalDate;
import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public record DriverFileRequest(
        Integer id,
        LocalDate expirationDate,
        String description,
        DriverFileTypeEnum type,
        Integer driverId,

        String driversLicense,
        Integer stateId,
        DriverFileClassEnum classType,
        LocalDate issuedDate,
        List<EndorsementRequest> endorsement,

        LocalDate lastCollectedOn,
        String socialSecurityNumber
) {
}
