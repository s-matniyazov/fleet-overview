package fleetoverview.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import fleetoverview.data.request.ActionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ActionEntity;
import fleetoverview.repository.ActionRepository;
import fleetoverview.service.ActionService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class ActionServiceImpl extends BaseService implements ActionService {
    private final ActionRepository repository;

    @Autowired
    public ActionServiceImpl(ActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<ActionEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    @Transactional
    public ApiResponse post(ActionRequest data) {
        repository.save(
                new ActionEntity(
                        data.name(),
                        data.description(),
                        data.url(),
                        data.type()
                )
        );
        return ApiResponse.success();
    }

    @Override
    @Transactional
    public ApiResponse put(ActionRequest data) {
        ActionEntity action = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("action.not_found")));

        action.setName(data.name());
        action.setDescription(data.description());
        action.setUrl(data.url());
        action.setType(data.type());

        repository.save(action);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(ActionRequest data) {
        ActionEntity action = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("action.not_found")));

        repository.delete(action);

        return ApiResponse.success();
    }
}
