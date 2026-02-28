package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.company.CompanyFileTypeEnum;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 июль 2025
 **/
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
