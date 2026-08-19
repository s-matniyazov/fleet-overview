package fleetoverview.repository.filter;

import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecification {

    public static Specification<CompanyEntity> specification(CompanyFilter filter) {
        if (filter == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return Specification.allOf(
                nameLike(filter.name()),
                dotEquals(filter.dot()),
                statusEquals(filter.status())
        );
    }

    public static Specification<CompanyEntity> nameLike(String name) {
        return (root, query, cb) -> (name == null || name.isBlank())
                ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%");
    }

    public static Specification<CompanyEntity> dotEquals(String dot) {
        return (root, query, cb) -> (dot == null || dot.isBlank())
                ? null
                : cb.equal(root.get("dot"), dot.trim());
    }

    public static Specification<CompanyEntity> statusEquals(CompanyStatusEnum status) {
        return (root, query, cb) -> (status == null)
                ? null
                : cb.equal(root.get("status"), status);
    }

    public static Specification<CompanyEntity> userIdEquals(Integer userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("createdById"), userId);
    }
}

