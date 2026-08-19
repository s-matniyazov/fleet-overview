package fleetoverview.repository.filter;

import fleetoverview.domain.enums.company.CompanyStatusEnum;

public record CompanyFilter (
        Integer id,
        String name,
        CompanyStatusEnum status,
        String dot
) { }
