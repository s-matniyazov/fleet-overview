package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.enums.TimeZoneEnum;
import fleetoverview.domain.enums.company.CompanyEntrantStatusEnum;
import fleetoverview.domain.enums.company.CompanyStatusEnum;


public record CompanyRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        String ownerName,
        String dot,
        String pinNumber,
        String mc,
        String dba,
        String fein,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int stateId,
        String address1,
        String address2,
        String city,
        String zipcode,
        String email,
        String phone,
        TimeZoneEnum timeZone,
        CompanyStatusEnum status,
        CompanyEntrantStatusEnum entrantStatus
) {
}
