package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 10 февр. 2025
 **/
public record ClientRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        String description,
        String shortName,
        String phone
) {
}
