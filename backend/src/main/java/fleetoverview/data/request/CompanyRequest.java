package fleetoverview.data.request;

import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.enums.TimeZoneEnum;
import fleetoverview.domain.enums.company.CompanyEntrantStatusEnum;
import fleetoverview.domain.enums.company.CompanyStatusEnum;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/
public record CompanyRequest(
        int id,
        String name,
        String ownerName,
        String dot,
        String pinNumber,
        String mc,
        String dba,
        String fein,
        int stateId,
        String address1,
        String address2,
        String city,
        Integer zipcode,
        String email,
        String phone,
        TimeZoneEnum timeZone,
        CompanyStatusEnum status,
        CompanyEntrantStatusEnum entrantStatus
) {
}
