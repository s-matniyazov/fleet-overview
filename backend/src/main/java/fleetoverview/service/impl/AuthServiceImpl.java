package fleetoverview.service.impl;

import fleetoverview.data.request.RegisterUserRequest;
import fleetoverview.repository.RoleRepository;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import fleetoverview.data.request.LoginRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.repository.UserRepository;
import fleetoverview.service.AuthService;
import fleetoverview.security.JwtService;
import fleetoverview.util.exceptions.ForbiddenException;
import fleetoverview.util.helper.Utils;

import java.util.Optional;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 19 февр. 2025
 **/
@Service
public class AuthServiceImpl extends BaseService implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(loginRequest.login());
        if (byUsername.isPresent()) {
            UserEntity user = byUsername.get();
            if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
                return DataResponse.success(jwtService.generateToken(user));
            }
        }
//        return ApiResponse.badRequest("there is no tomorrow");
        return ApiResponse.badRequest("Incorrect username or password");
    }

    @Override
    public ApiResponse unlock(LoginRequest loginRequest) {
        Authentication authentication = Utils.getAuthentication(true);

        UserEntity principal = (UserEntity) authentication.getPrincipal();

        if (!passwordEncoder.matches(loginRequest.password(), principal.getPassword())) {
            throw new ForbiddenException("Unauthorized");
        }

        return ApiResponse.success();
    }

    @Override
    public ApiResponse registerUser(RegisterUserRequest registerUserRequest) {
        userRepository.save(
                new UserEntity(
                        registerUserRequest.username(),
                        passwordEncoder.encode(registerUserRequest.password()),
                        registerUserRequest.name(),
                        registerUserRequest.email(),
                        roleRepository.findById(registerUserRequest.roleId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("role.not_found")))
                )
        );

        return ApiResponse.success();
    }
}
