package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.TimeZoneEnum;
import java.time.LocalDate;
import java.time.LocalTime;

public record CollisionRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String reportNumber,
        LocalDate collisionDate,
        LocalTime collisionTime,
        TimeZoneEnum timeZone,
        String city,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int countryId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int stateId,
        String zipCode,
        String typeOfCargo,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int companyId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int truckId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int driverId
) { }
