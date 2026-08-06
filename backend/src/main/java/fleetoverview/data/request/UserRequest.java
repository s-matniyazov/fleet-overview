package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public record UserRequest (
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String username,
        String password,
        String name,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int departmentsId,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int positionsId,
        String email,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int rolesId
) {}
