package fleetoverview.config;

import fleetoverview.domain.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditProvider")
public class AuditConfig {
    @Bean
    public AuditorAware<Integer> auditProvider(){
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .filter(a -> !(a instanceof AnonymousAuthenticationToken))
                .map(Authentication::getPrincipal)
                .filter(UserEntity.class::isInstance)   // your principal type
                .map(p -> ((UserEntity) p).getId());
    }
}
