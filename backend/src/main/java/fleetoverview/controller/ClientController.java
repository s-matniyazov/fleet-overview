package fleetoverview.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.ClientRequest;
import fleetoverview.domain.entity.ClientEntity;
import fleetoverview.service.ClientService;

import static fleetoverview.util.constants.UriConstants.CLIENTS;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 10 февр. 2025
 **/
@RestController
@RequestMapping(CLIENTS)
public class ClientController extends CrudController<ClientEntity, ClientRequest> {


    protected ClientController(ClientService service) {
        super(service);
    }
}
