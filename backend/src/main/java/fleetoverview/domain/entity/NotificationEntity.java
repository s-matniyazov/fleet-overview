package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 май 2025
 **/
@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseIdEntity {
    private String email;
    @Column(length = 200)
    private String subject;
    @Column(length = 4000)
    private String message;
    @Column(name = "is_send")
    private boolean send = false;

    public NotificationEntity() {
    }

    public NotificationEntity(String email, String subject, String message) {
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
