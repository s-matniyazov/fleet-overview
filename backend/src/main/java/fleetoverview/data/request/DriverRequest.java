package fleetoverview.data.request;

import fleetoverview.domain.enums.DriverStatusEnum;
import fleetoverview.domain.enums.driver.DriverTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DriverRequest(
        int id,
        int companyId,
        String firstName,
        String lastName,
        String middleName,
        LocalDate hireDate,
        LocalDate dateOfBirth,
        LocalDateTime terminationDate,
        int stateId,
        String address,
        String city,
        String zipCode,
        String email,
        String phone,
        DriverStatusEnum status,
        DriverTypeEnum type,
        int truckId
) {
}
