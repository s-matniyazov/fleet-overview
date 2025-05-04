package fleetoverview.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import fleetoverview.data.request.RoleRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.RoleEntity;
import fleetoverview.repository.ActionRepository;
import fleetoverview.repository.RoleRepository;
import fleetoverview.service.RoleService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
@Service
public class RoleServiceImpl extends BaseService implements RoleService {
    private final RoleRepository repository;
    private final ActionRepository actionRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository, ActionRepository actionRepository) {
        this.repository = repository;
        this.actionRepository = actionRepository;
    }

    @Override
    public DataResponse<List<RoleEntity>> get(Map<String,String> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(RoleRequest data) {
        repository.save(
                new RoleEntity(
                        data.name(),
                        data.description(),
                        data.actions().stream()
                                .map(it -> actionRepository.findById(it.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("action.not_found"))))
                                .collect(Collectors.toSet())
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(RoleRequest data) {
        RoleEntity action = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("action.not_found")));

        action.setName(data.name());
        action.setDescription(data.description());
        action.setRoleActions(
                data.actions().stream()
                        .map(it -> actionRepository.findById(it.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("action.not_found"))))
                        .collect(Collectors.toSet()));

        repository.save(action);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(RoleRequest data) {
        RoleEntity action = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("action.not_found")));

        repository.delete(action);

        return ApiResponse.success();
    }
}
