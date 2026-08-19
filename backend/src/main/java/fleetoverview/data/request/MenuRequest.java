package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.MenuStatusEnum;

import java.util.List;


public record MenuRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        String name,
        MenuStatusEnum status,
        List<MenuRequest> subMenus
) {}
