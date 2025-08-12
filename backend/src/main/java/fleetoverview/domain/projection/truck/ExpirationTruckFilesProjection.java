package fleetoverview.domain.projection.truck;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 май 2025
 **/
public interface ExpirationTruckFilesProjection {
    String getUnit();
    String getMaker();
    String getFuelType();
    Integer getYear();

    LocalDate getRegCabCardExp();
    LocalDate getAnnsInsExp();
    LocalDate getPhysDamageExp();
    LocalDate getLeaseAgrExp();
}
