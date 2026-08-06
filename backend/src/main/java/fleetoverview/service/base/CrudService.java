package fleetoverview.service.base;

import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;

import java.util.List;
import java.util.Map;


public interface CrudService<E, R> {
    DataResponse<List<E>> get(Map<String, Object> params);
    ApiResponse post(R data);
    ApiResponse put(R data);
    ApiResponse delete(R data);
}
