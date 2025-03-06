package fleetoverview.service;

import fleetoverview.data.request.UserRequest;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.service.base.CrudService;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public interface UserService extends CrudService<UserEntity, UserRequest> {}
