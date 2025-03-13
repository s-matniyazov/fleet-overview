package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.OwnerOperatorEntity;
import fleetoverview.service.OwnerOperatorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.OWNER_OPERATOR;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(OWNER_OPERATOR)
public class OwnerOperatorController extends CrudController<OwnerOperatorEntity, OnlyNameRequest> {
    protected OwnerOperatorController(OwnerOperatorService service) {
        super(service);
    }
}
