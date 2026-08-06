package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.StateRequest;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.service.StateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.STATE;


@RestController
@RequestMapping(STATE)
public class StateController extends CrudController<StateEntity, StateRequest> {
    protected StateController(StateService service) {
        super(service);
    }
}
