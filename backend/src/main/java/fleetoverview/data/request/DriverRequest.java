package fleetoverview.data.request;

import fleetoverview.domain.enums.DriverStatusEnum;
import fleetoverview.domain.enums.driver.DriverTypeEnum;

import java.time.LocalDate;

public record DriverRequest(
        int id,
        int companyId,
        String firstName,
        String lastName,
        String middleName,
        LocalDate hireDate,
        LocalDate dateOfBirth,
        LocalDate terminationDate,
        int stateId,
        String address,
        String city,
        int zipCode,
        String email,
        String phone,
        DriverStatusEnum status,
        DriverTypeEnum type,
        int truckId
) {
}
