package fleetoverview.service.impl;

import fleetoverview.data.request.InspectionFileRequest;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.inspection.InspectionEntity;
import fleetoverview.domain.entity.inspection.InspectionFileEntity;
import fleetoverview.service.InspectionService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class InspectionServiceImp extends BaseService implements InspectionService {

    private final SqlSessionFactory db;

    public InspectionServiceImp(SqlSessionFactory db) {
        this.db = db;
    }

    @Override
    public ApiResponse attachFile(InspectionFileRequest data, MultipartFile file) {
        return null;
    }

    @Override
    public DataResponse<List<InspectionFileEntity>> getFiles(Map<String, String> params) {
        return null;
    }

    @Override
    public DataResponse<List<InspectionEntity>> get(Map<String, Object> params) {
        if (!params.containsKey("companyId")) {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        try (SqlSession sqlSession = db.openSession()) {
            return DataResponse.success(
                    sqlSession.selectList("selectInspections", params)
            );
        }
    }

    @Override
    public ApiResponse post(InspectionRequest data) {
        return null;
    }

    @Override
    public ApiResponse put(InspectionRequest data) {
        return null;
    }

    @Override
    public ApiResponse delete(InspectionRequest data) {
        return null;
    }
}
