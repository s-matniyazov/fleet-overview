package fleetoverview.repository;

import fleetoverview.domain.entity.RefreshTokenEntity;
import fleetoverview.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenHash(String tokenHash);

    @Modifying
    @Transactional
    @Query("""
            update RefreshTokenEntity r set r.revoked = true
            where r.user = :user and r.revoked = false
            """)
    void revokeAllForUser(UserEntity user);

    @Modifying @Transactional
    void deleteByExpiresAtBefore(Instant cutoff);
}
