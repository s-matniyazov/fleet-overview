package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.truck.TruckModelMakerEntity;
import fleetoverview.service.base.CrudService;


public interface TruckModelMakerService extends CrudService<TruckModelMakerEntity, OnlyNameRequest> {
}
