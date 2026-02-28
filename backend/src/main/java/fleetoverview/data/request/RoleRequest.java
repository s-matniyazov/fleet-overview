package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.List;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
public record RoleRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        String description,
        List<ActionRequest> actions
) {}
