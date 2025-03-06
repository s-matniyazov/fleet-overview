package fleetoverview.service;

import fleetoverview.data.request.LoginRequest;
import fleetoverview.data.response.ApiResponse;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 19 февр. 2025
 **/
public interface AuthService {
    ApiResponse login(LoginRequest loginRequest);
    ApiResponse unlock(LoginRequest loginRequest);
}
