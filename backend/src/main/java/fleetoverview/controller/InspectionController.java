package fleetoverview.controller;

import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.inspection.InspectionEntity;
import fleetoverview.service.InspectionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.INSPECTION;

@RestController
@RequestMapping(INSPECTION)
public class InspectionController extends CrudController<InspectionEntity, InspectionRequest> {
    private final InspectionService service;
    protected InspectionController(InspectionService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("files")
    public HttpEntity<ApiResponse> getFiles(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(service.getFiles(params));
    }
}
