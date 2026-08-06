package fleetoverview.util.helper;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.function.Function;

import static fleetoverview.domain.enums.LangEnum.UZBEK;

@Component
public record MessageSourceBundle (
        MessageSource messageSource
) implements Function<String, String> {
    @Override
    public String apply(String code) {
        try {
            return messageSource.getMessage(
                    code,
                    null,
                    switch (UZBEK) {
                        case UZBEK -> Locale.forLanguageTag("UZ");
                        case RUSSIAN -> Locale.forLanguageTag("RU");
                        case KIRIL -> Locale.forLanguageTag("UK");
                        default -> Locale.ENGLISH;
                    }
            );
        } catch (Exception e) {
            return code;
        }
    }
}
