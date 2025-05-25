package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.trailer.TrailerModelMakerEntity;
import fleetoverview.service.TrailerModelMakerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.TRAILER_MODEL_MAKER;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 25 май 2025
 **/
@RestController
@RequestMapping(TRAILER_MODEL_MAKER)
public class TrailerModelMakerController extends CrudController<TrailerModelMakerEntity, OnlyNameRequest> {
    protected TrailerModelMakerController(TrailerModelMakerService service) {
        super(service);
    }
}
