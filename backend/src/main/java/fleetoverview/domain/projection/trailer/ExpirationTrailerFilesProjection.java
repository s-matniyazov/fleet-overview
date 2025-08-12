package fleetoverview.domain.projection.trailer;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 июль 2025
 **/
public interface ExpirationTrailerFilesProjection {
    String getUnit();
    String getMaker();
    Integer getYear();

    LocalDate getRegCabCardExp();
    LocalDate getAnnsInsExp();
    LocalDate getPhysDamageExp();
    LocalDate getLeaseAgrExp();
}
