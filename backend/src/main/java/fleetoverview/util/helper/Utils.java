package fleetoverview.util.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import fleetoverview.util.constants.UriConstants;
import fleetoverview.util.exceptions.ForbiddenException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Utils {

    public static boolean isNull(Object o) {return o == null;}

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
