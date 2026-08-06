package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.truck.FuelTypeEntity;
import fleetoverview.service.base.CrudService;


public interface FuelTypeService extends CrudService<FuelTypeEntity, OnlyNameRequest> {
}
