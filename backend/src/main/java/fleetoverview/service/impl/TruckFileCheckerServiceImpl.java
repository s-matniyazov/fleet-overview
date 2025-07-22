package fleetoverview.service.impl;

import fleetoverview.domain.entity.truck.TruckFileEntity;
import fleetoverview.domain.enums.truck.TruckFileStatusEnum;
import fleetoverview.repository.TruckFileRepository;
import fleetoverview.service.FileCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 12 июнь 2025
 **/
@Service
public class TruckFileCheckerServiceImpl implements FileCheckerService {
    private final TruckFileRepository repository;

    @Autowired
    public TruckFileCheckerServiceImpl(TruckFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkAndDisableFile() {
        List<TruckFileEntity> files = repository.findExpiredFiles().stream()
                .peek(it -> it.setStatus(TruckFileStatusEnum.PASSIVE)).toList();

        repository.saveAll(files);
    }
}
