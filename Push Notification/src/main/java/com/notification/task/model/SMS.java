package com.notification.task.model;

import com.notification.task.model.enums.Language;
import com.notification.task.model.enums.NotificationType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sms")
@Data
public class SMS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "is_sent")
    private boolean isSent;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Language lang;

    @Column(name = "sms_type")
    @Enumerated(EnumType.STRING)
    private NotificationType smsType;

    @Column(name = "created_at",    updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createdAt;

    @ManyToMany(mappedBy = "smsSet", fetch = FetchType.LAZY)
    private Set<Customer> customers = new HashSet<>();


}
