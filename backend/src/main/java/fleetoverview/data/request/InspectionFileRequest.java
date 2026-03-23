package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.domain.enums.inspection.InspectionFileTypeEnum;

public record InspectionFileRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) Integer id,
        @JsonSetter(nulls = Nulls.AS_EMPTY) Integer inspectionId,
        InspectionFileTypeEnum type,
        StatusEnum status
){}
