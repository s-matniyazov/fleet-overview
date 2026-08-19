package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.trailer.TrailerModelMakerEntity;
import fleetoverview.service.base.CrudService;


public interface TrailerModelMakerService extends CrudService<TrailerModelMakerEntity, OnlyNameRequest> {
}
