package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.CityRequest;
import fleetoverview.domain.entity.CityEntity;
import fleetoverview.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.CITY;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(CITY)
public class CityController extends CrudController<CityEntity, CityRequest> {
    protected CityController(CityService service) {
        super(service);
    }
}
