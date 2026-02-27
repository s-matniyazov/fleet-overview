package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.DriverStatusEnum;
import fleetoverview.domain.enums.driver.DriverTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DriverRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int companyId,
        String firstName,
        String lastName,
        String middleName,
        LocalDate hireDate,
        LocalDate dateOfBirth,
        LocalDateTime terminationDate,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int stateId,
        String address,
        String city,
        String zipCode,
        String email,
        String phone,
        DriverStatusEnum status,
        DriverTypeEnum type,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int truckId
) { }
