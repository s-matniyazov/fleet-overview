package fleetoverview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleetoverview.data.request.LoginRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.service.AuthService;

import static fleetoverview.util.constants.UriConstants.AUTH;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 19 февр. 2025
 **/
@RestController
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("login")
    public HttpEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }

    @PostMapping("unlock")
    public HttpEntity<ApiResponse> unlock(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.unlock(loginRequest));
    }
}
