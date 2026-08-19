package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.Role;
import fleetoverview.domain.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @AllArgsConstructor @Builder
public class UserEntity extends BaseEntity implements UserDetails {
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 32)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status = UserStatusEnum.A;

    private Date registrationDate;

    private boolean enableNotification = true;

    public UserEntity() {}

    public UserEntity(String username, String password, String name, String email) {
        this.username = email;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserEntity(Integer userId, String username) {
        this.setId(userId);
        this.setUsername(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .toList();
    }
}
