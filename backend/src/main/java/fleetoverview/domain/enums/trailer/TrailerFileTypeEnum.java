package fleetoverview.domain.enums.trailer;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public enum TrailerFileTypeEnum {
    REG_CAB_CARD("REG (CAB CARD)"),
    ANN_INS("ANN INS"),
    PHYS_DAMAGE("PHYS DAMAGE"),
    LEASE_AGR("LEASE AGR"),
    NON_TRUCKING_LIABILITY("NON-TRUCKING LIABILITY"),
    CLEAN_TRUCK_CHECK_PERMIT("CLEAN TRUCK CHECK PERMIT");

    private String description;

    TrailerFileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
