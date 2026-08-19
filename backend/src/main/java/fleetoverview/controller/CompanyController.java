package fleetoverview.controller;

import fleetoverview.data.request.CompanyFileRequest;
import fleetoverview.data.request.CompanyRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.repository.filter.CompanyFilter;
import fleetoverview.service.CompanyService;
import fleetoverview.service.impl.ExcelNotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.COMPANIES;


@RestController
@RequestMapping(COMPANIES)
@RequiredArgsConstructor
public class CompanyController{
    private final CompanyService service;
    private final ExcelNotificationServiceImpl excelNotificationService;

    @GetMapping
    protected HttpEntity<ApiResponse> findAll(@ParameterObject CompanyFilter filter) {
        return ResponseEntity.ok(service.findAll(filter));
    }

    @PostMapping
    protected HttpEntity<ApiResponse> post(@RequestBody CompanyRequest request) {
        return ResponseEntity.ok(service.post(request));
    }

    @PutMapping
    protected HttpEntity<ApiResponse> put(@RequestBody CompanyRequest request) {
        return ResponseEntity.ok(service.put(request));
    }

    @DeleteMapping
    protected HttpEntity<ApiResponse> delete(@RequestBody CompanyRequest request) {
        return ResponseEntity.ok(service.delete(request));
    }

    @PostMapping("attach-file")
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") CompanyFileRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachFile(data, file));
    }

    @PostMapping("excel")
    public HttpEntity<ApiResponse> excel() {
        excelNotificationService.sendNotifications();
        return ResponseEntity.ok(ApiResponse.badRequest("Post:excel \"Bad Request\""));
    }

    @GetMapping("files")
    protected HttpEntity<ApiResponse> getFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getFiles(params));
    }

    @GetMapping("with-files")
    protected HttpEntity<ApiResponse> getWithFiles(@RequestParam Map<String,String> params) {
        return ResponseEntity.ok(service.getWithFiles(params));
    }

    @PostMapping("{id}/deactivate")
    public HttpEntity<ApiResponse> deactivate(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deactivate(id));
    }

    @PostMapping("{id}/activate")
    public HttpEntity<ApiResponse> activate(@PathVariable Integer id) {
        return ResponseEntity.ok(service.activate(id));
    }
}
