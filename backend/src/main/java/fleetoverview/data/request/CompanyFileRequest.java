package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.company.CompanyFileTypeEnum;

import java.time.LocalDate;

public record CompanyFileRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        LocalDate expirationDate,
        String filedPeriod,
        LocalDate nextUpdateDate,
        String description,
        CompanyFileTypeEnum type,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int companyId
) {
}
