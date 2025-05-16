package fleetoverview.domain.enums;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public enum ActionTypesEnum {
    ACTION(1), MODULE(2);
    private final int value;

    ActionTypesEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
