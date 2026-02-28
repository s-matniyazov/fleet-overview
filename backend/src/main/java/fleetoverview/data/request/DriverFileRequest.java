package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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
        @JsonSetter(nulls = Nulls.AS_EMPTY) Integer id,
        LocalDate expirationDate,
        String description,
        DriverFileTypeEnum type,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Integer driverId,

        String driversLicense,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Integer stateId,
        DriverFileClassEnum classType,
        LocalDate issuedDate,
        List<EndorsementRequest> endorsement,

        LocalDate lastCollectedOn,
        String socialSecurityNumber
) {
}
