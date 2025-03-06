package fleetoverview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.MenuRequest;
import fleetoverview.domain.entity.MenuEntity;
import fleetoverview.service.MenuService;

import static fleetoverview.util.constants.UriConstants.MENUS;

/**
 * @author :  qobiljon.qodirjonov
 * @mailto :  1810092kk@gmail.com
 * @created : 28 янв. 2025
 **/
@RestController
@RequestMapping(value = MENUS)
public class MenuController extends CrudController<MenuEntity, MenuRequest> {
    @Autowired
    protected MenuController(MenuService service) {
        super(service);
    }
}
