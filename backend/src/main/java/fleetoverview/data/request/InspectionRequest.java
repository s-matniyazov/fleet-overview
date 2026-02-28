package fleetoverview.data.request;

import java.time.LocalDate;

public record InspectionRequest(
        int  id,
        String inspectionNumber,
        LocalDate inspection_date,
        int driverId,
        int coDriverId,
        int companyId,
        String description,
        int stateId,
        String city,
        String location,
        boolean violation_discovered
) {}
