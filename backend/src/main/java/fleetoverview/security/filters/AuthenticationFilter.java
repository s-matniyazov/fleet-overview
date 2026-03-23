package fleetoverview.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import fleetoverview.util.helper.Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 19 февр. 2025
 **/
@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (Utils.isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            unSuccess(response);
            return;
        }

        chain.doFilter(request, response);
    }

    private void unSuccess(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("Unauthenticated");
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
