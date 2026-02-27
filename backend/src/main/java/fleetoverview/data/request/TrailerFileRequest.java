package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import fleetoverview.domain.enums.trailer.TrailerFileTypeEnum;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public record TrailerFileRequest(
        @JsonSetter(nulls = Nulls.AS_EMPTY) int id,
        Date expirationDate,
        String description,
        TrailerFileTypeEnum type,
        @JsonSetter(nulls = Nulls.AS_EMPTY) int trailerId
) { }
