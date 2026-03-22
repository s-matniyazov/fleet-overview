package fleetoverview.service.impl;

import fleetoverview.domain.entity.inspection.Inspection;
import fleetoverview.repository.InspectionRepository;
import fleetoverview.service.FileCheckerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionFileCheckerServiceImpl implements FileCheckerService {
    private final InspectionRepository repository;

    public InspectionFileCheckerServiceImpl(InspectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkAndDisableFile() {
    }
}
