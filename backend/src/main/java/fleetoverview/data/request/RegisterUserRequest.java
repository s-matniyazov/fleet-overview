package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 22 июль 2025
 **/
public record RegisterUserRequest(
        String username,
        String password,
        String name,
        String email,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int roleId
) {
}
