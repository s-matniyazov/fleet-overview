package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.service.TruckService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static fleetoverview.util.constants.UriConstants.TRUCK;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
@RestController
@RequestMapping(TRUCK)
public class TruckController extends CrudController<TruckEntity, TruckRequest> {
    private final TruckService service;
    protected TruckController(TruckService service) {
        super(service);
        this.service = service;
    }

    @PostMapping("attach-file")
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") TruckFileRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachFileToTruck(data, file));
    }
}
