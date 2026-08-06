package fleetoverview.domain.enums;


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
