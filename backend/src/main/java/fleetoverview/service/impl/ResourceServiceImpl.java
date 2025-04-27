package fleetoverview.service.impl;

import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.repository.ResourceRepository;
import fleetoverview.service.ResourceService;
import fleetoverview.util.exceptions.FileException;
import fleetoverview.util.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository repository;

    @Autowired
    public ResourceServiceImpl(ResourceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void downloadResource(Integer id, HttpServletResponse response) {
        ResourceEntity resource = repository.findById(id).orElseThrow(() -> new NotFoundException("file.not_found"));

        response.setContentType(resource.getContentType());

        try {
            File file = new File(resource.getPath());

            FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            throw new NotFoundException("system.file.not_found");
        }
    }

    @Transactional
    @Override
    public ResourceEntity createResource(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new FileException("File is empty !!!");

        String string = UUID.randomUUID().toString();
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        string += "." + split[split.length - 1];

        ResourceEntity save = repository.save(new ResourceEntity(
                file.getOriginalFilename(),
                split[split.length - 1],
                file.getSize(),
                "resources//" + string,
                file.getContentType()
        ));

        saveFileToSystem(save, file);

        return save;
    }

    @Override
    public void removeResource(ResourceEntity resource) {
        // todo: should remove file from system and db
    }

    private void saveFileToSystem(ResourceEntity resource, MultipartFile file) {
        Path path = Paths.get(resource.getPath());
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }
}
