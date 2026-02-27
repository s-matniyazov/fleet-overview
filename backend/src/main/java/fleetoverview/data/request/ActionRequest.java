package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.ActionTypesEnum;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
public record ActionRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        String description,
        String url,
        ActionTypesEnum type
) {}
