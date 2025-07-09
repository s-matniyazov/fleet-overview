package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.CompanyRequest;
import fleetoverview.data.request.CompanyFileRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.service.CompanyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.COMPANIES;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/

@RestController
@RequestMapping(COMPANIES)
public class CompanyController extends CrudController<CompanyEntity, CompanyRequest> {
    private final CompanyService service;
    protected CompanyController(CompanyService service) {
        super(service);
        this.service = service;
    }

    @PostMapping("attach-file")
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") CompanyFileRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachFile(data, file));
    }

    @GetMapping("files")
    protected HttpEntity<ApiResponse> getFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getFiles(params));
    }

    @GetMapping("with-files")
    protected HttpEntity<ApiResponse> getWithFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getWithFiles(params));
    }
}
