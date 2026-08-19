package fleetoverview.service;

import fleetoverview.data.request.UserRequest;
import fleetoverview.data.response.DataResponse;
import fleetoverview.data.response.UserResponse;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.service.base.CrudService;

import java.util.List;


public interface UserService extends CrudService<UserEntity, UserRequest> {
    DataResponse<List<UserResponse>> findAll();
}
