package fleetoverview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.RoleRequest;
import fleetoverview.domain.entity.RoleEntity;
import fleetoverview.service.RoleService;

import static fleetoverview.util.constants.UriConstants.ROLES;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
@RestController
@RequestMapping(ROLES)
public class RoleController extends CrudController<RoleEntity, RoleRequest> {
    @Autowired
    protected RoleController(RoleService service) {
        super(service);
    }
}
