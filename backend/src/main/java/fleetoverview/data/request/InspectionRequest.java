package fleetoverview.data.request;

import java.time.LocalDate;
import java.util.List;

public record InspectionRequest(
        Integer id,
        String inspectionNumber,
        LocalDate inspectionDate,
        Integer driverId,
        Integer coDriverId,
        Integer truckId,
        Integer companyId,
        String description,
        Integer stateId,
        String city,
        String location,
        Boolean violationDiscovered,
        Integer countryId,
        Integer inspectionLevel,
        Integer totalFineAmount,
        Boolean outOfService,
        List<ViolationTypeRequest> violationTypes
) {
}