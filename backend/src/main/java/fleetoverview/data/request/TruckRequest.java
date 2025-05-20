package fleetoverview.data.request;

import fleetoverview.domain.enums.TruckStatusEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
public record TruckRequest(
        int id,
        String unit,
        Date inServiceDate,
        String licensePlate,
        int stateId,
        int modelMakerId,
        int year,
        int fuelTypeId,
        double grossWeight,
        int axles,
        String vin,
        int ownershipTypeId,
        boolean includeIFTA,
        int purchaseTypeId,
        int driverId,
        String description,
        TruckStatusEnum status,
        int companyId
) { }
