package fleetoverview.data.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public record OnlyNameRequest(@JsonSetter(nulls = Nulls.AS_EMPTY) int id, String name) {}
