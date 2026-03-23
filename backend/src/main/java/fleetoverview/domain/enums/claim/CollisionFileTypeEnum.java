package fleetoverview.domain.enums.claim;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 июнь 2025
 **/
public enum CollisionFileTypeEnum {
    SIMPLE("Simple");

    private String description;

    CollisionFileTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
