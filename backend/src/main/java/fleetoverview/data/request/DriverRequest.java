package fleetoverview.data.request;

import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.domain.entity.StateEntity;

import java.sql.Date;


public record DriverRequest(
        String firstName,
        String lastName,
        String middleName,
        Date hireDate,
        Date dateOfBirth,
        StateEntity state,
        CountryEntity country,
        String address,
        String city,
        int zipCode,
        String email,
        String phone


) {
}
