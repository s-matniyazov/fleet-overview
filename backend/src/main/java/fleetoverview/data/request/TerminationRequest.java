package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public record TerminationRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int driverId,
        String reason
) {
}
