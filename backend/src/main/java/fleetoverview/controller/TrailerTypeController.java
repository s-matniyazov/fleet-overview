package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.trailer.TrailerTypeEntity;
import fleetoverview.service.TrailerTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.TRAILER_TYPE;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 25 май 2025
 **/
@RestController
@RequestMapping(TRAILER_TYPE)
public class TrailerTypeController extends CrudController<TrailerTypeEntity, OnlyNameRequest> {
    protected TrailerTypeController(TrailerTypeService service) {
        super(service);
    }
}