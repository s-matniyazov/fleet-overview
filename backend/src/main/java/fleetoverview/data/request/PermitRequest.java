package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.PermitTypeEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 июнь 2025
 **/
public record PermitRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        Date expirationDate,
        String description,
        PermitTypeEnum type
) {
}
