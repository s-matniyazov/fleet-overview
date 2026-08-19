package fleetoverview.service.impl;

import fleetoverview.data.LoginRequest;
import fleetoverview.data.RefreshRequest;
import fleetoverview.data.RegisterRequest;
import fleetoverview.data.TokenResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.domain.enums.Role;
import fleetoverview.domain.enums.UserStatusEnum;
import fleetoverview.repository.UserRepository;
import fleetoverview.security.JwtProperties;
import fleetoverview.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final JwtProperties props;


    @Transactional
    public DataResponse<TokenResponse> login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        UserEntity user = userRepository.findByEmail(req.email()).orElseThrow();
        return DataResponse.success(buildToken(user));
    }

    @Transactional
    public DataResponse<TokenResponse> register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }
        UserEntity user = UserEntity.builder()
                .email(req.email())
                .username(req.email())
                .name(req.name())
                .password(passwordEncoder.encode(req.password()))
                .roles(Set.of(Role.USER))
                .status(UserStatusEnum.A)
                .build();
        userRepository.save(user);
        return DataResponse.success(buildToken(user));
    }

    @Transactional
    public void logout(String rawRefreshToken) {
        UserEntity user = refreshTokenService.verifyAndConsume(rawRefreshToken);
        refreshTokenService.revokeAll(user);
    }

    @Transactional
    public DataResponse<TokenResponse> refresh(RefreshRequest req) {
        UserEntity user = refreshTokenService.verifyAndConsume(req.refreshToken());
        return DataResponse.success(buildToken(user));
    }

    private TokenResponse buildToken(UserEntity user) {
        return new TokenResponse(
                jwtService.generateAccessToken(user),
                refreshTokenService.create(user),
                "Bearer",
                props.accessTokenTtl().toSeconds());
    }

}
