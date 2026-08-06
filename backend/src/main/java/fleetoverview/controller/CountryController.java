package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.COUNTRY;


@RestController
@RequestMapping(COUNTRY)
public class CountryController extends CrudController<CountryEntity, OnlyNameRequest> {
    protected CountryController(CountryService service) {
        super(service);
    }
}
