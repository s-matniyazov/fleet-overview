package fleetoverview.service.base;

import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;

import java.util.List;
import java.util.Map;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public interface CrudService<E, R> {
    DataResponse<List<E>> get(Map<String,String> params);
    ApiResponse post(R data);
    ApiResponse put(R data);
    ApiResponse delete(R data);
}
