
package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
public record StateRequest(@JsonSetter(nulls = Nulls.AS_EMPTY) int id, String name, @JsonSetter(nulls = Nulls.AS_EMPTY) int countryId) {}
