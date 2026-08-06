
package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public record StateRequest(@JsonSetter(nulls = Nulls.AS_EMPTY) int id, String name, @JsonSetter(nulls = Nulls.AS_EMPTY) int countryId) {}
