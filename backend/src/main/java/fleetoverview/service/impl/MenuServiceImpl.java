package fleetoverview.service.impl;

import org.springframework.stereotype.Service;
import fleetoverview.data.request.MenuRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.MenuEntity;
import fleetoverview.repository.MenuRepository;
import fleetoverview.service.MenuService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author :  qobiljon.qodirjonov
 * @mailto :  1810092kk@gmail.com
 * @created : 29 янв. 2025
 **/
@Service
public class MenuServiceImpl extends BaseService implements MenuService {
    private final MenuRepository repository;

    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<MenuEntity>> get(Map<String,String> params) {
        return DataResponse.success(repository.findAll());
    }

    @Override
    public ApiResponse post(MenuRequest data) {
        repository.save(new MenuEntity(
                        data.name(),
                        data.status(),
                        data.subMenus().stream()
                                .map(it -> repository.findById(it.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("menu.not_found"))))
                                .toList()
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(MenuRequest data) {
        MenuEntity menu = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("menu.not_found")));

        menu.setName(data.name());
        menu.setStatus(data.status());
        menu.setSubMenuEntities(data.subMenus().stream()
                .map(it -> repository.findById(it.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("menu.not_found"))))
                .collect(Collectors.toList())
        );
        repository.save(menu);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(MenuRequest data) {
        repository.deleteById(data.id());
        return ApiResponse.success();
    }
}
