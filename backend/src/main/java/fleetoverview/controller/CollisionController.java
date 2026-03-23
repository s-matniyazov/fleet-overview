package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.CollisionRequest;
import fleetoverview.domain.entity.claim.CollisionEntity;
import fleetoverview.service.CollisionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.COLLISION;

@RestController
@RequestMapping(COLLISION)
public class CollisionController extends CrudController<CollisionEntity, CollisionRequest> {
    public CollisionController(CollisionService service) {
        super(service);
    }
}
