package fleetoverview.data.request;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 22 июль 2025
 **/
public record RegisterUserRequest(
        String username,
        String password,
        String name,
        String email,
        int roleId
) {
}
