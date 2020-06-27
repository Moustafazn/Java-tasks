package com.notification.task.model;

import com.notification.task.model.enums.Language;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rider")
@Data
public class Rider extends User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_id")
    private Long riderId;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Language lang;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at",    updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "rider_notification",
            joinColumns = {
                    @JoinColumn(name = "rider_id", referencedColumnName = "rider_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "notification_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private Set<PushNotification> pushNotifications = new HashSet<>();


}
