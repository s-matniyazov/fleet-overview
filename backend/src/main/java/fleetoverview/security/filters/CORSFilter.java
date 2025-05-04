package fleetoverview.security.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 27 сент. 2024
 **/
@Component
public class CORSFilter implements Filter {
    Logger log = LoggerFactory.getLogger(CORSFilter.class);

    public CORSFilter() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter {}", CORSFilter.class.getName());

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With, remember-me");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("CORSFilter init");
    }

    @Override
    public void destroy() {
        log.info("CORSFilter destroyed");
    }

}
