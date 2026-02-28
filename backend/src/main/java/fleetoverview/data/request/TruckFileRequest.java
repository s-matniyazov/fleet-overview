package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.truck.TruckFileTypeEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public record TruckFileRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        Date expirationDate,
        String description,
        TruckFileTypeEnum type,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int truckId
) {}
