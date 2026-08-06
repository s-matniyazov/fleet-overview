package fleetoverview.data;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String type,
        long expiresIn
) {}
