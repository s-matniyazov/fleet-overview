package fleetoverview.controller;

import fleetoverview.controller.base.BaseController;
import fleetoverview.service.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

import static fleetoverview.util.constants.UriConstants.RESOURCES;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 26 апр. 2025
 **/
@RestController
@RequestMapping(RESOURCES)
public class ResourceController extends BaseController {
    private final ResourceService service;

    public ResourceController(ResourceService service) {
        this.service = service;
    }

    @GetMapping(value = "view/{id}")
    public void view(@PathVariable Integer id, HttpServletResponse response){
        service.downloadResource(id, response);
    }
}
