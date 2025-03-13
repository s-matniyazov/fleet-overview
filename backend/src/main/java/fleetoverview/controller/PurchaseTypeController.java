package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.PurchaseTypeEntity;
import fleetoverview.service.PurchaseTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.PURCHASE_TYPE;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(PURCHASE_TYPE)
public class PurchaseTypeController extends CrudController<PurchaseTypeEntity, OnlyNameRequest> {
    protected PurchaseTypeController(PurchaseTypeService service) {
        super(service);
    }
}
