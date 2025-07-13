package fleetoverview.domain.enums.truck;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public enum TruckFileTypeEnum {
    REG_CAB_CARD("REG (CAB CARD)"),
    ANN_INS("ANN INS"),
    PHYS_DAMAGE("PHYS DAMAGE"),
    LEASE_AGR("LEASE AGR"),
    NON_TRUCKING_LIABILITY("NON-TRUCKING LIABILITY"),
    CLEAN_TRUCK_CHECK_PERMIT("CLEAN TRUCK CHECK PERMIT");

    private String description;

    TruckFileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
