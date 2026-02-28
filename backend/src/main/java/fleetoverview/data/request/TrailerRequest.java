package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.truck.TruckStatusEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public record TrailerRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String unit,
        String licensePlate,
        Date inServiceDate,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int modelMakerId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int typeId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int year,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int axles,
        @JsonSetter(nulls = Nulls.AS_EMPTY) double length,
        @JsonSetter(nulls = Nulls.AS_EMPTY) double height,
        String vin,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int ownershipTypeId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int purchaseTypeId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int driverId,
        String description,
        TruckStatusEnum status,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int companyId
) { }
