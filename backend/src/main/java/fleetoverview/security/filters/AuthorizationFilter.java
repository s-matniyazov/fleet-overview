package fleetoverview.security.filters;

import fleetoverview.domain.entity.RoleEntity;
import fleetoverview.repository.RoleRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.repository.UserRepository;
import fleetoverview.security.JwtService;
import fleetoverview.util.helper.Utils;

import java.io.IOException;
import java.util.Optional;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 19 февр. 2025
 **/
@Component
public class AuthorizationFilter implements Filter {
    private final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public AuthorizationFilter(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        if (Utils.isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            unSuccess(response);
            return;
        }

        String jwt = token.substring(7);
        Optional<UserEntity> byUsername = userRepository.findByUsername(jwtService.getUsername(jwt));
        if (byUsername.isPresent()) {
            UserEntity user = byUsername.get();

            RoleEntity roleWithActions = roleRepository.findRoleWithActions(user.getRole().getId());
            if (roleWithActions != null) {
                user.setRole(roleWithActions);
            }

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getRole().getRoleActions()
            ));
        } else {
            log.error("Invalid JWT token");
            unSuccess(response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("AuthorizationFilter init");
    }

    @Override
    public void destroy() {
        log.info("AuthorizationFilter destroyed");
    }

    private void unSuccess(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"Unauthorized\"}");
    }
}
