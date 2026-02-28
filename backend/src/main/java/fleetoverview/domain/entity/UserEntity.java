package fleetoverview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.LangEnum;
import fleetoverview.domain.enums.UserStatusEnum;

import java.util.Collection;
import java.util.Collections;
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

//    @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
//    @JoinColumn(referencedColumnName = "id", name = "roles_id")
//    private RoleEntity role;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status = UserStatusEnum.P;

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

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @JsonIgnore
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
