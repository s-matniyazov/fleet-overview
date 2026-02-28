package fleetoverview.data.request;

import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.domain.enums.inspection.InspectionFileTypeEnum;

public record InspectionFileRequest(
        int id ,
        InspectionFileTypeEnum type,
        StatusEnum status,
        String description
        ){}
