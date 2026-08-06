package fleetoverview.domain.enums;


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
