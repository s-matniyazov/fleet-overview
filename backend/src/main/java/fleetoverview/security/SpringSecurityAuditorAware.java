package fleetoverview.security;

import fleetoverview.domain.entity.UserEntity;
import fleetoverview.util.helper.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;

import java.util.Objects;
import java.util.Optional;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 март 2025
 **/
public class SpringSecurityAuditorAware implements AuditorAware<Integer> {
    private final Logger log = LoggerFactory.getLogger(SpringSecurityAuditorAware.class);

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = Utils.getAuthentication(false);

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof UserEntity user) {
            return Optional.of(user.getId());
        }

        return Optional.empty();
    }
}
