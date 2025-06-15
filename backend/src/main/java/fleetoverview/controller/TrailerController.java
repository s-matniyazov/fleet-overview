package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.PermitRequest;
import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.service.TrailerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.TRAILER;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
@RestController
@RequestMapping(TRAILER)
public class TrailerController extends CrudController<TrailerEntity, TrailerRequest> {
    private final TrailerService service;
    protected TrailerController(TrailerService service) {
        super(service);
        this.service = service;
    }

    @PostMapping("attach-file")
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") TrailerFileRequest data, @RequestPart(name = "file") MultipartFile file) {
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