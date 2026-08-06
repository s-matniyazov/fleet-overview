package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.OwnershipTypeEntity;
import fleetoverview.service.base.CrudService;


public interface OwnershipTypeService extends CrudService<OwnershipTypeEntity, OnlyNameRequest> {
}
