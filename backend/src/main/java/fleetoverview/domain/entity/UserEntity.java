package fleetoverview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.LangEnum;
import fleetoverview.domain.enums.UserStatusEnum;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
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
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status = UserStatusEnum.A;

    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    private LangEnum lang = LangEnum.KIRIL;
    private boolean enableNotification = true;

    public UserEntity() {}

    public UserEntity(String username, String password, String name, String email) {
        this.username = username;
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
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }
}
