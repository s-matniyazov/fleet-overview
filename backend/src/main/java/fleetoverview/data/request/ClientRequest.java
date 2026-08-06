package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public record ClientRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        String description,
        String shortName,
        String phone
) {
}
