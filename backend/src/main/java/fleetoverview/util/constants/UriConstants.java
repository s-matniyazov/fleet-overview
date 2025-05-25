package fleetoverview.util.constants;

/**
 * @author :  qobiljon.qodirjonov
 * @mailto :  1810092kk@gmail.com
 * @created : 28 янв. 2025
 **/
public interface UriConstants {
    String API = "/api";
    String AUTH = "/api/auth";
    String RESOURCES = "/api/resources";

    String USERS = API + "/users";
    String ROLES = API + "/roles";
    String ACTIONS = API + "/actions";

    String CLIENTS = API + "/clients";
    String COMPANIES = API + "/companies";

    String COUNTRY = API + "/countries";
    String STATE = API + "/states";
    String FUEL_TYPE = API + "/fuel-types";
    String OWNERSHIP_TYPE = API + "/ownership-type";
    String PURCHASE_TYPE = API + "/purchase-types";

    // truck
    String TRUCK = API + "/trucks";
    String TRUCK_MODEL_MAKER = API + "/truck/model-makers";
    // trailer
    String TRAILER = API + "/trailers";
    String TRAILER_TYPE = API + "/trailer-types";
    String TRAILER_MODEL_MAKER = API + "/trailer/model-makers";
    // driver
    String DRIVER = API + "/drivers";
}
