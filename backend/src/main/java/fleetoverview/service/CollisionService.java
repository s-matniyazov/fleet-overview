package fleetoverview.service;

import fleetoverview.data.request.CollisionRequest;
import fleetoverview.domain.entity.claim.CollisionEntity;
import fleetoverview.service.base.CrudService;

public interface CollisionService extends CrudService<CollisionEntity, CollisionRequest> {
}
