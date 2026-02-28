package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 15 июль 2025
 **/
public record TerminationRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int driverId,
        String reason
) {
}
