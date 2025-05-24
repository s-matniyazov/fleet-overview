package fleetoverview.data.request;

import fleetoverview.domain.enums.truck.TruckStatusEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public record TrailerRequest(
        int id,
        String unit,
        String licensePlate,
        Date inServiceDate,
        int modelMakerId,
        int typeId,
        int year,
        int axles,
        double length,
        double height,
        String vin,
        int ownershipTypeId,
        int purchaseTypeId,
        int driverId,
        String description,
        TruckStatusEnum status,
        int companyId
) { }
