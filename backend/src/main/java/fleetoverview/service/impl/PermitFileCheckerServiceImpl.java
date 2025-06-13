package fleetoverview.service.impl;

import fleetoverview.domain.entity.PermitEntity;
import fleetoverview.domain.entity.truck.TruckFileEntity;
import fleetoverview.domain.enums.PermitStatusEnum;
import fleetoverview.repository.PermitRepository;
import fleetoverview.service.FileCheckerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 июнь 2025
 **/
@Service
public class PermitFileCheckerServiceImpl implements FileCheckerService {
    private final PermitRepository permitRepository;

    public PermitFileCheckerServiceImpl(PermitRepository permitRepository) {
        this.permitRepository = permitRepository;
    }

    @Override
    public void checkAndDisableFile() {

        List<PermitEntity> files = permitRepository.findExpiredFiles().stream()
                .peek(it -> it.setStatus(PermitStatusEnum.PASSIVE)).toList();

        permitRepository.saveAll(files);
    }
}
