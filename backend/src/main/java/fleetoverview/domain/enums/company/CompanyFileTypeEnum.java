package fleetoverview.domain.enums.company;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 июль 2025
 **/
public enum CompanyFileTypeEnum {
    INS_CERT("Company Insurance Certificate"),
    IFTA_LICENSE("IFTA License"),
    UCR("UCR (Unified Carrier Registration)"),
    HVUT_2290("HVUT 2290"),
    W_9("W-9"),
    MCS_150("MCS-150 (Biennial)");

    private final  String description;

    CompanyFileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
