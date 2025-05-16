package fleetoverview.data.request;

import fleetoverview.domain.enums.TruckFileTypeEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public record TruckFileRequest(
        int id,
        Date expirationDate,
        String description,
        TruckFileTypeEnum type,
        int truckId
) {}
