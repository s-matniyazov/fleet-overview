package fleetoverview.domain.projection.company;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 08 окт. 2025
 **/
public interface CompanyFilesProjection {
    String getName();

    LocalDate getInsuranceCertExp();
    LocalDate getIftaExp();
    LocalDate getUcrExp();
    LocalDate getPermitExp();
    LocalDate getMcsExp();
}
