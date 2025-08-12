package fleetoverview.domain.projection.driver;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 11 авг. 2025
 **/
public interface ExpirationDriverFilesProjection {
    String getDriverName();

    LocalDate getCdlExp();
    LocalDate getMedicalCertExp();
    LocalDate getMvr();
    LocalDate getClearingHouseExp();
    LocalDate getSsnExp();

    LocalDate getCcfExp();
    LocalDate getDrugTestResultExp();
    LocalDate getDriverApplicationExp();
    LocalDate getPevExp();
}
