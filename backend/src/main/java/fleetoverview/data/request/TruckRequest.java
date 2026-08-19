package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.truck.TruckStatusEnum;

import java.sql.Date;


public record TruckRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String unit,
        Date inServiceDate,
        String licensePlate,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int stateId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int modelMakerId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int year,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int fuelTypeId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) double grossWeight,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int axles,
        String vin,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int ownershipTypeId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) boolean includeIFTA,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int purchaseTypeId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int driverId,
        String description,
        TruckStatusEnum status,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int companyId,
        String carrierResponsibleForSafety
) { }
