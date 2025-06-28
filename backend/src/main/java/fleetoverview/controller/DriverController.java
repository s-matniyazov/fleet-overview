package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.DriverRequest;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.service.DriverService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.DRIVER;


@RestController
@RequestMapping(DRIVER)
public class DriverController extends CrudController<DriverEntity, DriverRequest> {
    private final DriverService service;
    protected DriverController(DriverService service) {
        super(service);
        this.service = service;
    }

}
