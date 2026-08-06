package fleetoverview.service;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.service.base.CrudService;


public interface CountryService extends CrudService<CountryEntity, OnlyNameRequest> {
}
