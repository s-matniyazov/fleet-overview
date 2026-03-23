package fleetoverview.security;

import fleetoverview.util.exceptions.ForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import fleetoverview.domain.entity.UserEntity;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 18 февр. 2025
 **/
@Component
public class JwtService {
    private final String jwtSecret = "Qobil-bro-is-good-for-java-coding-problems-in-spring";
    private final long jwtExpirationDate = 3600000; //1h = 3600s and 3600*1000 = 3600000 milliseconds

    public String generateToken(UserEntity user) {
        String username = user.getUsername();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .claims(
                        Map.of(
                                "id", user.getId(),
                                "username", user.getUsername(),
                                "role", "USER"
                        )
                )
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // extract username from JWT token
    public ClaimType getUsername(String token) {
        try {
            Claims payload = Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new ClaimType(
                    payload.get("id", Integer.class),
                    payload.get("username", String.class),
                    payload.get("role", String.class)
            );
        } catch (Exception e) {
            throw new ForbiddenException("forbidden");
        }

    }

    // validate JWT token
    public boolean validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;

    }

    public record ClaimType(Integer userId, String username, String role) {
    }
}
