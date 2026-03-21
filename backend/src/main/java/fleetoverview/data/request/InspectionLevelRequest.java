package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
public record InspectionLevelRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        Integer level,
        String description
) {}
