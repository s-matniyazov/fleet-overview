package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.CompanyRequest;
import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.service.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fleetoverview.util.constants.UriConstants.COMPANIES;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/

@RestController
@RequestMapping(COMPANIES)
public class CompanyController extends CrudController<CompanyEntity, CompanyRequest> {
    protected CompanyController(CompanyService service) {
        super(service);
    }
}
