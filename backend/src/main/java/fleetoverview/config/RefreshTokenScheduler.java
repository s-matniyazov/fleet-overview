package fleetoverview.config;

import fleetoverview.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenScheduler {

    private final RefreshTokenRepository repository;

    @Scheduled(cron = "0 0 3 * * SUN") // Runs once a week (Sunday at 3:00 AM)
    public void cleanUpRefreshTokens() {
        log.info("Cleaning REFRESH TOKENS");
        repository.deleteByExpiresAtBefore(Instant.now());
    }
}
