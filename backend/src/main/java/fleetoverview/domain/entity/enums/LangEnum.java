package fleetoverview.domain.entity.enums;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public enum LangEnum {
    UZBEK("uz"),KIRIL("ka"), RUSSIAN("ru"), ENGLISH("en");
    private final String value;

    LangEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
