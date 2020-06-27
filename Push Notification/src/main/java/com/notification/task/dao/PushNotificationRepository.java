package com.notification.task.dao;

import com.notification.task.model.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("pushNotificationRepository")
public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {

    List<PushNotification> findAllByIsSent(boolean isSent);

}
