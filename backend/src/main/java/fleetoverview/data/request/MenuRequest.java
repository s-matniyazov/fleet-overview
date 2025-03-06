package fleetoverview.data.request;

import fleetoverview.domain.entity.enums.MenuStatusEnum;

import java.util.List;

/**
 * @author :  qobiljon.qodirjonov
 * @mailto :  1810092kk@gmail.com
 * @created : 28 янв. 2025
 **/
public record MenuRequest(
        int id,
        String name,
        MenuStatusEnum status,
        List<MenuRequest> subMenus
) {}
