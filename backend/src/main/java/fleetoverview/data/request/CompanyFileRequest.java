package fleetoverview.data.request;

import fleetoverview.domain.enums.company.CompanyFileTypeEnum;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 июль 2025
 **/
public record CompanyFileRequest(
        int id,
        LocalDate expirationDate,
        String filedPeriod,
        LocalDate nextUpdateDate,
        String description,
        CompanyFileTypeEnum type,
        int companyId
) {
}
