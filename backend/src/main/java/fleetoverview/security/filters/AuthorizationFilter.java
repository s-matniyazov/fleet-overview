package fleetoverview.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.security.JwtService;
import fleetoverview.util.helper.Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 19 февр. 2025
 **/
@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);
    private final JwtService jwtService;

    public AuthorizationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Transactional
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

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
        Optional<JwtService.ClaimType> byUsername = jwtService.getUsername(jwt);
        if(byUsername.isEmpty()) {
            unSuccess(response);
            return;
        }

        JwtService.ClaimType claimType = byUsername.get();
        UserEntity user = new UserEntity(claimType.userId(), claimType.username());

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.singleton(new SimpleGrantedAuthority(claimType.role()))
        ));

        chain.doFilter(request, response);
    }

    private void unSuccess(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\":\"Unauthorized\"}");
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

}
