package fleetoverview.domain.projection;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 май 2025
 **/
public interface ExpirationTruckFilesProjection {
    String getTruckUnit();
    String getTruckMaker();
    String getTruckFuelType();

    LocalDate getRegCabCardExp();
    LocalDate getAnnsInsExp();
    LocalDate getPhysDamageExp();
    LocalDate getLeaseAgrExp();
}
