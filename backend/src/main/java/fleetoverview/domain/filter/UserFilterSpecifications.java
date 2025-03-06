package fleetoverview.domain.filter;

import org.springframework.data.jpa.domain.Specification;
import fleetoverview.domain.entity.UserEntity;
import fleetoverview.domain.entity.enums.UserStatusEnum;
import fleetoverview.util.helper.Utils;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 февр. 2025
 **/
public class UserFilterSpecifications {
    public static Specification<UserEntity> hasName(String name) {
        if (Utils.isNull(name)) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<UserEntity> hasStatus(UserStatusEnum status) {
        if (Utils.isNull(status)) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
