package fleetoverview.controller;

import fleetoverview.data.request.InspectionLevelRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.inspection.InspectionLevelsRef;
import fleetoverview.service.InspectionLevelService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fleetoverview.util.constants.UriConstants.INSPECTION_LEVEL;


@RestController
@RequestMapping(INSPECTION_LEVEL)
public class InspectionLevelsController {

    private final InspectionLevelService inspectionLevelService;

    public InspectionLevelsController(InspectionLevelService inspectionLevelService) {
        this.inspectionLevelService = inspectionLevelService;
    }

    @GetMapping
    public HttpEntity<ApiResponse> getInspectionLevels() {
        return ResponseEntity.ok(inspectionLevelService.getAll());
    }

    @PostMapping
    public HttpEntity<ApiResponse> getInspectionLevels(@RequestBody InspectionLevelRequest data) {
        return ResponseEntity.ok(inspectionLevelService.save(data));
    }

    @DeleteMapping
    public HttpEntity<ApiResponse> delete(@RequestParam Integer id) {
        return ResponseEntity.ok(inspectionLevelService.delete(id));
    }

}
