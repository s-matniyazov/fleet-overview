package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.DriverFileRequest;
import fleetoverview.data.request.DriverRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.service.DriverService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.DRIVER;

@RestController
@RequestMapping(DRIVER)
public class DriverController extends CrudController<DriverEntity, DriverRequest> {
    private final DriverService service;
    protected DriverController(DriverService service) {
        super(service);
        this.service = service;
    }

    @PostMapping("attach-file")
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") DriverFileRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachFile(data, file));
    }

    @GetMapping("files")
    protected HttpEntity<ApiResponse> getFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getFiles(params));
    }

    @GetMapping("endorsements")
    protected HttpEntity<ApiResponse> getEndorsements() {
        return ResponseEntity.ok(service.getEndorsements());
    }
}
