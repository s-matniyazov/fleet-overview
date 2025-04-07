package fleetoverview.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.security.core.userdetails.UserDetails;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.enums.LangEnum;
import fleetoverview.domain.entity.enums.UserStatusEnum;

import java.util.Collection;
import java.util.Date;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {
    @Column(nullable = false, length = 50)
    private String username;
    private String password;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "roles_id")
    private RoleEntity role;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status = UserStatusEnum.P;

    private Date registrationDate;

    @Enumerated(EnumType.STRING)
    private LangEnum lang = LangEnum.KIRIL;
    private boolean enableNotification = true;

    public UserEntity() {}

    public UserEntity(String username, String password, String name, String email, RoleEntity roleEntity) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = roleEntity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<ActionEntity> getAuthorities() {
        return role.getRoleActions();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LangEnum getLang() {
        return lang;
    }

    public void setLang(LangEnum lang) {
        this.lang = lang;
    }

    public boolean isEnableNotification() {
        return enableNotification;
    }

    public void setEnableNotification(boolean enableNotification) {
        this.enableNotification = enableNotification;
    }
}
