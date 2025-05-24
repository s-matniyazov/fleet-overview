package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.truck.FuelTypeEntity;
import fleetoverview.service.FuelTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.FUEL_TYPE;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(FUEL_TYPE)
public class FuelTypeController extends CrudController<FuelTypeEntity, OnlyNameRequest> {
    protected FuelTypeController(FuelTypeService service) {
        super(service);
    }
}
