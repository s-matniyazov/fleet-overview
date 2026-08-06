package fleetoverview.service.impl;

import fleetoverview.domain.entity.RefreshTokenEntity;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.repository.RefreshTokenRepository;
import fleetoverview.security.JwtProperties;
import fleetoverview.security.JwtService;
import fleetoverview.util.exceptions.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;
    private final JwtProperties properties;

    @Transactional
    public String create(UserEntity user) {
        String refreshToken = jwtService.generateRefreshToken();
        repository.save(RefreshTokenEntity.builder()
                .tokenHash(sha256(refreshToken))
                .user(user)
                .expiresAt(Instant.now().plus(properties.refreshTokenTtl()))
                .build());

        return refreshToken;
    }

    /** Validates + remove old gives new: old token is revoked, caller gets a fresh one. */
    @Transactional
    public UserEntity verifyAndConsume(String refreshToken) {
        RefreshTokenEntity stored = repository.findByTokenHash(sha256(refreshToken))
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not recognized"));

        if (stored.isRevoked()) {
            repository.revokeAllForUser(stored.getUser());
            throw new InvalidRefreshTokenException("Refresh token already used");
        }
        if (stored.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token expired");
        }
        stored.setRevoked(true);
        repository.save(stored);
        return stored.getUser();
    }

    @Transactional
    public void revokeAll(UserEntity user) {
        repository.revokeAllForUser(user);
    }

    private String sha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
