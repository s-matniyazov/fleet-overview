package fleetoverview.service.impl;

import org.springframework.stereotype.Service;
import fleetoverview.data.request.ClientRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ClientEntity;
import fleetoverview.repository.ClientRepository;
import fleetoverview.service.ClientService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 10 февр. 2025
 **/
@Service
public class ClientServiceImpl extends BaseService implements ClientService {
    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<ClientEntity>> get(Map<String,String> params) {
        return DataResponse.success(repository.findAll());
    }

    @Override
    public ApiResponse post(ClientRequest data) {
        repository.save(
                new ClientEntity(
                        data.name(),
                        data.description(),
                        data.shortName(),
                        data.phone()
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(ClientRequest data) {
        ClientEntity action = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("client.not_found")));

        action.setName(data.name());
        action.setDescription(data.description());
        action.setShortName(data.shortName());
        action.setPhone(data.phone());

        repository.save(action);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(ClientRequest data) {
        ClientEntity action = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("client.not_found")));

        repository.delete(action);

        return ApiResponse.success();
    }
}
