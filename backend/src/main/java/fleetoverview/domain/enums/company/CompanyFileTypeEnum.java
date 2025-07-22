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
    MCS_150("MCS-150 (Biennial)"),
    CT_PERMIT("CT Permit"),

    // other documents
    ARTICLES_OF_INCORPORATION("Articles of Incorporation"),
    MC_CERTIFICATE("MC Certificate"),
    OWNER_OPERATOR_AGREEMENT("Owner operator Agreement"),
    DRIVER_AGREEMENT("Driver Agreement");

    private final  String description;

    CompanyFileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
