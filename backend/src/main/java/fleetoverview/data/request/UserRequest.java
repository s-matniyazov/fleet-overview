package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public record UserRequest (
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String username,
        String password,
        String name,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int departmentsId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int positionsId,
        String email,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int rolesId
) {}
