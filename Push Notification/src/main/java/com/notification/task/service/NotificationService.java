package com.notification.task.service;

import com.notification.task.exception.ApplicationException;
import com.notification.task.model.Customer;
import com.notification.task.model.PushNotification;
import com.notification.task.model.Rider;
import com.notification.task.model.SMS;

import java.util.Set;

public interface NotificationService {

    void sendPersonalSMSMessage(Customer customer, SMS sms) throws ApplicationException;

    void sendGroupSMSMessage(Set<Customer> customer, SMS sms) throws ApplicationException;

    void sendPersonalNotificationMessage(Rider rider, PushNotification notification);

    void sendGlobalNotificationMessage(PushNotification notification);

    void sendGroupNotificationMessage(Set<Rider> Rider,  PushNotification notification);
}
