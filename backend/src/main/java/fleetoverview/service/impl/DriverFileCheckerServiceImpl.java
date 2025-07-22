package fleetoverview.service.impl;

import fleetoverview.domain.entity.driver.DriverFileEntity;
import fleetoverview.domain.enums.driver.DriverFileStatusEnum;
import fleetoverview.repository.DriverFileRepository;
import fleetoverview.service.FileCheckerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 22 июль 2025
 **/
@Service
public class DriverFileCheckerServiceImpl implements FileCheckerService {
    private final DriverFileRepository repository;

    public DriverFileCheckerServiceImpl(DriverFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkAndDisableFile() {
        List<DriverFileEntity> files = repository.findExpiredFiles().stream()
                .peek(it -> it.setStatus(DriverFileStatusEnum.PASSIVE)).toList();

        repository.saveAll(files);
    }
}
