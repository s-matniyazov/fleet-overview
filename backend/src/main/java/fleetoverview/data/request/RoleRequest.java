package fleetoverview.data.request;

import java.util.List;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
public record RoleRequest(
        int id,
        String name,
        String description,
        List<ActionRequest> actions
) {}
