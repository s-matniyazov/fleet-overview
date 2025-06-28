package fleetoverview.domain.enums.driver;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 июнь 2025
 **/
public enum DriverTypeEnum {
    OWNER_OPERATOR("OWNER OPERATOR"),
    COMPANY_DRIVER("COMPANY DRIVER"),
    INDEPENDENT_CONTRACTOR("INDEPENDENT CONTRACTOR");

    private String description;

    DriverTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
