package fleetoverview.domain.enums;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 июнь 2025
 **/
public enum PermitTypeEnum {
    OR("Oregon"),
    NM("New Mexico"),
    KY("Kentucky"),
    NY("New York"),
    CN("Connecticut");

    private String description;

    PermitTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
