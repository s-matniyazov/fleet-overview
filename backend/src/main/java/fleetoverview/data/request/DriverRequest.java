package fleetoverview.data.request;

import java.sql.Date;

public record DriverRequest(
        int id,
        int companyId,
        String firstName,
        String lastName,
        String middleName,
        Date hireDate,
        Date dateOfBirth,
        int stateId,
        String address,
        String city,
        int zipCode,
        String email,
        String phone,
        String status
) {
}
