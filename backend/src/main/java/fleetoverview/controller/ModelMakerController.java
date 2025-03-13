package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.ModelMakerEntity;
import fleetoverview.service.ModelMakerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.MODEL_MAKER;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(MODEL_MAKER)
public class ModelMakerController extends CrudController<ModelMakerEntity, OnlyNameRequest> {
    protected ModelMakerController(ModelMakerService service) {
        super(service);
    }
}
