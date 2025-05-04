package fleetoverview.service.impl;

import fleetoverview.data.request.CompanyRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.service.CompanyService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.ExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/
@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {
    private final CompanyRepository repository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<CompanyEntity>> get(Map<String, String> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(CompanyRequest data) {

        repository.save(
                new CompanyEntity(
                        data.name(),
                        data.description(),
                        data.address()
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(CompanyRequest data) {

        CompanyEntity company = repository.findById(data.id()).orElseThrow(() -> new ExistsException(mSourceBundle.apply("company.found")));

        company.setName(data.name());
        company.setDescription(data.description());
        company.setAddress(data.address());

        repository.save(company);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(CompanyRequest data) {
        return ApiResponse.success();
    }
}
