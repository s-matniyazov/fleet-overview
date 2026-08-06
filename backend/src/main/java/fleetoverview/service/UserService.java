package fleetoverview.service;

import fleetoverview.data.request.UserRequest;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.service.base.CrudService;


public interface UserService extends CrudService<UserEntity, UserRequest> {}
