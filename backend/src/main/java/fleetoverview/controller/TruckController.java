package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.domain.entity.TruckEntity;
import fleetoverview.service.TruckService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.TRUCK;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
@RestController
@RequestMapping(TRUCK)
public class TruckController extends CrudController<TruckEntity, TruckRequest> {
    protected TruckController(TruckService service) {
        super(service);
    }
}
