package fleetoverview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.ActionRequest;
import fleetoverview.domain.entity.ActionEntity;
import fleetoverview.service.ActionService;

import static fleetoverview.util.constants.UriConstants.ACTIONS;


@RestController
@RequestMapping(ACTIONS)
public class ActionController extends CrudController<ActionEntity, ActionRequest> {
    @Autowired
    protected ActionController(ActionService service) {
        super(service);
    }
}
