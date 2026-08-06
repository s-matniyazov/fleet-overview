package fleetoverview.controller;

import fleetoverview.data.request.InspectionFileRequest;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.PagedResponseContainer;
import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.service.InspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.INSPECTION;

@RestController
@RequiredArgsConstructor
@RequestMapping(INSPECTION)
public class InspectionController {
    private final InspectionService service;

    @PostMapping()
    public HttpEntity<ApiResponse> save(@RequestBody InspectionRequest data) {
        return ResponseEntity.ok(service.save(data));
    }

    @GetMapping
    public HttpEntity<ApiResponse> getInspections(@RequestParam("companyId") Integer companyId,
                                                  @RequestParam(name = "page", required = false) Integer page,
                                                  @RequestParam(name = "size", required = false) Integer size,
                                                  @RequestParam(name = "driverName", required = false) String driverName,
                                                  @RequestParam(name = "status", required = false) StatusEnum status) {
        return ResponseEntity.ok(new PagedResponseContainer<>(service.getInspections(
                PageRequest.of(page == null ? 0 : Math.max(page - 1, 0),
                        size == null ? 0 : size),
                companyId,
                driverName,
                status)));

    }

    @PostMapping(
            value = "attach-file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public HttpEntity<ApiResponse> attachFile(@RequestPart(name = "data") InspectionFileRequest data, @RequestPart(name = "file") MultipartFile file) {
        return ResponseEntity.ok(service.attachFile(data, file));
    }

    @GetMapping("files")
    public HttpEntity<ApiResponse> getFiles(@RequestParam Integer inspectionId) {
        return ResponseEntity.ok(service.getFiles(inspectionId));
    }

    @GetMapping("violation-types")
    public HttpEntity<ApiResponse> getViolationTypes(@RequestParam Integer inspectionId) {
        return ResponseEntity.ok(service.getViolationTypes(inspectionId));
    }

    @PostMapping("update-status")
    public HttpEntity<ApiResponse> deactivate(@RequestParam Integer inspectionId) {
        return ResponseEntity.ok(service.updateStatus(inspectionId));
    }
}
