package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.service.TrailerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("{id}/deactivate")
    public HttpEntity<ApiResponse> deactivate(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deactivate(id));
    }

    @PostMapping("{id}/activate")
    public HttpEntity<ApiResponse> activate(@PathVariable Integer id) {
        return ResponseEntity.ok(service.activate(id));
    }

    @PostMapping("attach-file")
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") TrailerFileRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachFile(data, file));
    }

    @GetMapping("files")
    protected HttpEntity<ApiResponse> getFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getFiles(params));
    }
}