package fleetoverview.domain.enums.driver;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 июнь 2025
 **/
public enum DriverFileClassEnum {
    A("A Class"),
    B("B Class"),
    C("C Class"),
    D("D Class");

    private String description;

    DriverFileClassEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
