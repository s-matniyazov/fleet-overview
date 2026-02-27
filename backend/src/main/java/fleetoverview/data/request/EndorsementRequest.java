package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public record EndorsementRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name
) {
}
