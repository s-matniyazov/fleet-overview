package fleetoverview.controller.base;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.service.base.CrudService;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 янв. 2025
 **/
public abstract class CrudController<E, R> extends BaseController {
    private final java.util.logging.Logger logger = Logger.getLogger(CrudController.class.getName());
    protected final CrudService<E, R> service;

    protected CrudController(CrudService<E, R> service) {
        this.service = service;
    }

    @GetMapping
    protected HttpEntity<ApiResponse> get(@RequestParam Map<String, Object> params) {
        return ResponseEntity.ok(service.get(params));
    }

    @PostMapping
    protected HttpEntity<ApiResponse> post(@RequestBody R request) {
        return ResponseEntity.ok(service.post(request));
    }

    @PutMapping
    protected HttpEntity<ApiResponse> put(@RequestBody R request) {
        return ResponseEntity.ok(service.put(request));
    }

    @DeleteMapping
    protected HttpEntity<ApiResponse> delete(@RequestBody R request) {
        return ResponseEntity.ok(service.delete(request));
    }
}
