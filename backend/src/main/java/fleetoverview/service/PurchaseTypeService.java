package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.PurchaseTypeEntity;
import fleetoverview.service.base.CrudService;


public interface PurchaseTypeService extends CrudService<PurchaseTypeEntity, OnlyNameRequest> {
}
