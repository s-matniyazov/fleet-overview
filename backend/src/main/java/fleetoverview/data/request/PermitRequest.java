package fleetoverview.data.request;

import fleetoverview.domain.enums.PermitTypeEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 июнь 2025
 **/
public record PermitRequest(
        int id,
        Date expirationDate,
        String description,
        PermitTypeEnum type
) {
}
