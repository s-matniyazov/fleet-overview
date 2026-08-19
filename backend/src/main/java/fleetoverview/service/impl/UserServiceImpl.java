package fleetoverview.service.impl;

import fleetoverview.data.RegisterRequest;
import fleetoverview.data.request.UserRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.data.response.UserResponse;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.repository.RoleRepository;
import fleetoverview.repository.UserRepository;
import fleetoverview.service.UserService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final AuthServiceImpl authService;

    @Override
    public DataResponse<List<UserEntity>> get(Map<String, Object> params) {
        return null;
    }

    @Override
    public DataResponse<List<UserResponse>> findAll() {
        List<UserResponse> id = repository.findAll().stream()
                .map(e -> new UserResponse(
                        e.getId(),
                        e.getName(),
                        e.getEmail(),
                        e.getRoles(),
                        e.getStatus()
                ))
                .collect(Collectors.toList());

        return DataResponse.success(id);
    }

    @Override
    public ApiResponse post(UserRequest data) {
        authService.register(new RegisterRequest(
                data.email(),
                data.password(),
                data.name()
        ));
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(UserRequest data) {
        UserEntity user = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("user.not_found")));

        user.setName(data.name());
        user.setPassword(data.password());
        user.setName(data.name());
        user.setEmail(data.email());
//        user.setRole(roleRepository.findById(data.rolesId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("role.not_found"))));

        repository.save(user);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(UserRequest data) {
        UserEntity user = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("user.not_found")));

        repository.delete(user);

        return ApiResponse.success();
    }
}
