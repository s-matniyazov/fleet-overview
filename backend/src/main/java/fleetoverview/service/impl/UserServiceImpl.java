package fleetoverview.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import fleetoverview.data.request.UserRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.domain.entity.enums.UserStatusEnum;
import fleetoverview.domain.filter.UserFilterSpecifications;
import fleetoverview.repository.RoleRepository;
import fleetoverview.repository.UserRepository;
import fleetoverview.service.UserService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 янв. 2025
 **/
@Service
public class UserServiceImpl extends BaseService implements UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Override
    public DataResponse<List<UserEntity>> get(Map<String, String> params) {
        Specification<UserEntity> filters = Specification.where(CollectionUtils.isEmpty(params) ? null : UserFilterSpecifications.hasName(params.get("name")))
                                                         .and(Objects.requireNonNull(CollectionUtils.isEmpty(params) ? null : UserFilterSpecifications.hasStatus(UserStatusEnum.valueOf(params.get("status")))));

        return DataResponse.success(repository.findAll(filters, Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(UserRequest data) {
        repository.save(
                new UserEntity(
                        data.username(),
                        data.password(),
                        data.name(),
                        data.email(),
                        roleRepository.findById(data.rolesId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("role.not_found")))
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(UserRequest data) {
        UserEntity user = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("user.not_found")));

        user.setName(data.name());
        user.setPassword(data.password());
        user.setName(data.name());
        user.setEmail(data.email());
        user.setRole(roleRepository.findById(data.rolesId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("role.not_found"))));

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
