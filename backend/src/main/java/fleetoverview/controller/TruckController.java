package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.PermitRequest;
import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.service.TruckService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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
        return ResponseEntity.ok(service.attachFile(data, file));
    }

    @PostMapping("{id}/attach-permit")
    public HttpEntity<ApiResponse> attachPermit(@PathVariable("id") int id, @RequestPart(name = "data") PermitRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachPermit(id, data, file));
    }

    @GetMapping("files")
    protected HttpEntity<ApiResponse> getFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getFiles(params));
    }

    @GetMapping("permits")
    protected HttpEntity<ApiResponse> getPermits(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getPermits(params));
    }
}
