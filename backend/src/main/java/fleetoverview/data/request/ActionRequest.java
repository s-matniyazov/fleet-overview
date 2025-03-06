package fleetoverview.data.request;

import fleetoverview.domain.entity.enums.ActionTypesEnum;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
public record ActionRequest(
        int id,
        String name,
        String description,
        String url,
        ActionTypesEnum type
) {}
