package fleetoverview.domain.enums.driver;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 июнь 2025
 **/
public enum DriverFileTypeEnum {
    CDL("CDL (Front)"),
    MEDICAL_CERT("Medical Certificate"),
    MVR("MVR"),
    CLEARING_HOUSE("Clearing House"),
    SSN("SSN"),

    CCF("CCF"),
    DRUG_TEST_RESULT("DRUG TEST RESULT"),
    DRIVER_APPLICATION("DRIVER APPLICATION"),
    PEV("PEV");

    private String description;

    DriverFileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
