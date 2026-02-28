package fleetoverview.domain.enums.inspection;

public enum InspectionFileTypeEnum {
    CORRECTION("Correction"),
    PROOF_OF_CERTIFICATION("Proof Of Certification");

    private String description;
    InspectionFileTypeEnum(String description) {this.description = description;}
    public String getDescription() {return description;}
    public void setDescription (String description) {this.description = description;}
}
