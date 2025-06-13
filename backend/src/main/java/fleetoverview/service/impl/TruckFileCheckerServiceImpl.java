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
    private final TruckFileRepository truckFileRepository;

    @Autowired
    public TruckFileCheckerServiceImpl(TruckFileRepository truckFileRepository) {
        this.truckFileRepository = truckFileRepository;
    }

    @Override
    public void checkAndDisableFile() {
        List<TruckFileEntity> files = truckFileRepository.findExpiredFiles().stream().map(it -> {
            it.setStatus(TruckFileStatusEnum.PASSIVE);
            return it;
        }).toList();

        truckFileRepository.saveAll(files);
    }
}
