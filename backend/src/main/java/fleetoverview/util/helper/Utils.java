package fleetoverview.util.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import fleetoverview.util.constants.UriConstants;
import fleetoverview.util.exceptions.ForbiddenException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 февр. 2025
 **/
public class Utils {
    private static final Set<String> publicUrls = Set.of(
            "/error",
            "/v3/api-docs/",
            "/api/auth/login",
            "/swagger-ui/index.html"
    );
    public static boolean isNull(Object o) {return o == null;}
    public static boolean isPublicPath(String o) {
        return publicUrls.contains(o) || o.contains("swagger-ui") || o.contains("api-docs") || o.contains("fleet")
                || o.contains("js") || o.contains("html") || o.contains("css") || o.contains("ico") || o.contains("jpg") || o.contains("svg");
    }

    public static List<String> getUrls() {
        Field[] fields = UriConstants.class.getFields();
        return Arrays.stream(fields).map(it -> {
            try {
                return it.get(null).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public static Authentication getAuthentication(boolean raise) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null && raise) {
            throw new ForbiddenException("Unauthorized");
        }

        return authentication;
    }
}
