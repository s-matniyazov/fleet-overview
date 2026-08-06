package fleetoverview.domain.enums;

public enum UserStatusEnum {
    A(1), P(2);
    private final int value;

    UserStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
