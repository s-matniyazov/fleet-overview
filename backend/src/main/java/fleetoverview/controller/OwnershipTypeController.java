package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.OwnershipTypeEntity;
import fleetoverview.service.OwnershipTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.OWNERSHIP_TYPE;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(OWNERSHIP_TYPE)
public class OwnershipTypeController extends CrudController<OwnershipTypeEntity, OnlyNameRequest> {
    protected OwnershipTypeController(OwnershipTypeService service) {
        super(service);
    }
}
