package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.util.List;


public record RoleRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        String description,
        List<ActionRequest> actions
) {}
