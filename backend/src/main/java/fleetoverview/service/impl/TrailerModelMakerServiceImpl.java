package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.trailer.TrailerModelMakerEntity;
import fleetoverview.repository.TrailerModelMakerRepository;
import fleetoverview.service.TrailerModelMakerService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class TrailerModelMakerServiceImpl extends BaseService implements TrailerModelMakerService {
    private final TrailerModelMakerRepository repository;

    @Autowired
    public TrailerModelMakerServiceImpl(TrailerModelMakerRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<TrailerModelMakerEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    @Transactional
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new TrailerModelMakerEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    @Transactional
    public ApiResponse put(OnlyNameRequest data) {
        TrailerModelMakerEntity modelMaker = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("modelMaker.not_found")));

        modelMaker.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        TrailerModelMakerEntity modelMaker = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("modelMaker.not_found")));

        repository.delete(modelMaker);

        return ApiResponse.success();
    }
}
