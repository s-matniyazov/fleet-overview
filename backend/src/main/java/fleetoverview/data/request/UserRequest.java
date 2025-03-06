package fleetoverview.data.request;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public record UserRequest (
        int id,
        String username,
        String password,
        String name,
        int departmentsId,
        int positionsId,
        String email,
        int rolesId
) {}
