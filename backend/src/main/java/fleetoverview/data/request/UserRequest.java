package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public record UserRequest (
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        @JsonSetter(nulls = Nulls.AS_EMPTY) String username,
        String password,
        String name,
        String email,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int rolesId
) {}
