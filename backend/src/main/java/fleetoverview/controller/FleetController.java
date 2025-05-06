package fleetoverview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 май 2025
 **/
@Controller
public class FleetController {
    @RequestMapping(value = {"/fleet", "/fleet/{path:[^\\.]*}"})
    public String index() {
        return "forward:/fleet/index.html";
    }
}
