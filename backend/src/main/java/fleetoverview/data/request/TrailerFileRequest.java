package fleetoverview.data.request;

import fleetoverview.domain.enums.trailer.TrailerFileTypeEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public record TrailerFileRequest(
        int id,
        Date expirationDate,
        String description,
        TrailerFileTypeEnum type,
        int trailerId
) { }
