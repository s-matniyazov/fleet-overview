package fleetoverview.domain.projection.inspection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fleetoverview.domain.entity.inspection.ViolationTypes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionDto {

    // --- Inspection ---
    private Integer id;
    private String inspectionNumber;
    private LocalDate inspectionDate;
    private Integer companyId;
    private Integer truckId;
    private String unitNumber;
    private String description;
    private boolean violationDiscovered;
    private String status;
    private Integer stateId;
    private String location;
    private String city;
    private Integer totalFineAmount;
    private Integer inspectionLevel;
    private Boolean outOfService;

    // --- Driver ---
    private Integer driverId;
    private String driverFirstName;
    private String driverLastName;
    private Integer coDriverId;

    private Set<InspectionFileDto> files = new HashSet<>();

    public InspectionDto(
            Integer in_id,
            String in_inspection_number,
            LocalDate in_inspection_date,
            Integer in_company_id,
            String in_unit,
            String in_description,
            boolean in_violation_discovered,
            String status,
            Integer stateId,
            String location,
            String city,
            Integer totalFineAmount,
            Integer inspectionLevel,
            Boolean outOfService,
            Integer truckId,
            Integer dr_id,
            String dr_first_name,
            String dr_last_name,
            Integer coDriverId,
            Integer fi_cor_id,
            String fi_cor_type,
            String fi_cor_status,
            String fi_cor_description,
            Integer fi_cer_id,
            String fi_cer_type,
            String fi_cer_status,
            String fi_cer_description,
            String cor_file_name,
            Long cor_size,
            String cer_file_name,
            Long cer_size
    ) {
        this.id = in_id;
        this.inspectionNumber = in_inspection_number;
        this.inspectionDate = in_inspection_date;
        this.companyId = in_company_id;
        this.unitNumber = in_unit;
        this.description = in_description;
        this.violationDiscovered = in_violation_discovered;
        this.driverId = dr_id;
        this.driverFirstName = dr_first_name;
        this.driverLastName = dr_last_name;
        this.status = status;
        this.stateId = stateId;
        this.location = location;
        this.city = city;
        this.totalFineAmount = totalFineAmount;
        this.inspectionLevel = inspectionLevel;
        this.outOfService = outOfService;
        this.truckId = truckId;
        this.coDriverId = coDriverId;
        if (fi_cor_id != null) {
            this.files.add(new InspectionFileDto(fi_cor_id, fi_cor_type, fi_cor_status, fi_cor_description, cor_file_name, cor_size));
        }
        if (fi_cer_id != null) {
            this.files.add(new InspectionFileDto(fi_cer_id, fi_cer_type, fi_cer_status, fi_cer_description, cer_file_name, cer_size));
        }

    }
}
