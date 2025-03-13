package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.COUNTRY;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@RestController
@RequestMapping(COUNTRY)
public class CountryController extends CrudController<CountryEntity, OnlyNameRequest> {
    protected CountryController(CountryService service) {
        super(service);
    }
}
