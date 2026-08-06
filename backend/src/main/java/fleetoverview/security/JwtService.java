package fleetoverview.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties properties;

    private SecretKey key =

    public String extractUsername(String token) {
        return extractClaim();
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims =
        return null;
    }
}
