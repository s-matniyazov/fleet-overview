package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.truck.TruckModelMakerEntity;
import fleetoverview.service.TruckModelMakerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.TRUCK_MODEL_MAKER;


@RestController
@RequestMapping(TRUCK_MODEL_MAKER)
public class TruckModelMakerController extends CrudController<TruckModelMakerEntity, OnlyNameRequest> {
    protected TruckModelMakerController(TruckModelMakerService service) {
        super(service);
    }
}
