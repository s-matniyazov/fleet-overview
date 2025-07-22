package fleetoverview.service.impl;

import fleetoverview.domain.entity.company.CompanyFileEntity;
import fleetoverview.domain.enums.company.CompanyFileStatusEnum;
import fleetoverview.repository.CompanyFileRepository;
import fleetoverview.service.FileCheckerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 22 июль 2025
 **/
@Service
public class CompanyFileCheckerServiceImpl implements FileCheckerService {
    private final CompanyFileRepository repository;

    public CompanyFileCheckerServiceImpl(CompanyFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkAndDisableFile() {
        List<CompanyFileEntity> files = repository.findExpiredFiles().stream()
                .peek(it -> it.setStatus(CompanyFileStatusEnum.PASSIVE)).toList();

        repository.saveAll(files);
    }
}
