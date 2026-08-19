package fleetoverview.controller;

import fleetoverview.data.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fleetoverview.controller.base.CrudController;
import fleetoverview.data.request.UserRequest;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.service.UserService;

import java.util.Map;

import static fleetoverview.util.constants.UriConstants.USERS;


@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController{

    private final UserService service;

    @GetMapping
    protected HttpEntity<ApiResponse> get() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    protected HttpEntity<ApiResponse> post(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.post(request));
    }

    @PutMapping
    protected HttpEntity<ApiResponse> put(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.put(request));
    }

    @DeleteMapping
    protected HttpEntity<ApiResponse> delete(@RequestBody UserRequest request) {
        return ResponseEntity.ok(service.delete(request));
    }
}
