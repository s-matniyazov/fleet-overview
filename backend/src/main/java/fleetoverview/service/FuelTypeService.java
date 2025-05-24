package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.truck.FuelTypeEntity;
import fleetoverview.service.base.CrudService;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
public interface FuelTypeService extends CrudService<FuelTypeEntity, OnlyNameRequest> {
}
