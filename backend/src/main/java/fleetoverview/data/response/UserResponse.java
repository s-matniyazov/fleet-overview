package fleetoverview.data.response;

import fleetoverview.domain.enums.Role;
import fleetoverview.domain.enums.UserStatusEnum;

import java.util.Set;

public record UserResponse (
        Integer id,
        String name,
        String email,
        Set<Role> role,
        UserStatusEnum status
) { }
