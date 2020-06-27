package com.notification.task.service.impl;

import com.notification.task.components.ActiveUserSessionComponent;
import com.notification.task.controller.NotificationController;
import com.notification.task.dao.CustomerRepository;
import com.notification.task.dao.PushNotificationRepository;
import com.notification.task.dao.RiderRepository;
import com.notification.task.dao.SMSRepository;
import com.notification.task.exception.ApplicationException;
import com.notification.task.model.Customer;
import com.notification.task.model.PushNotification;
import com.notification.task.model.Rider;
import com.notification.task.model.SMS;
import com.notification.task.model.enums.NotificationType;
import com.notification.task.service.NotificationService;
import com.notification.task.service.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DefaultNotificationService implements NotificationService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SMSRepository smsRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private PushNotificationRepository pushNotificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Value(value = "${sms.config.numberOfSMSesPerMinute}")
    private int maxNumOfSMSPerMinute;


    private final NotificationController notificationController;

    @Autowired
    public DefaultNotificationService(@Lazy NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @Override
    public void sendPersonalSMSMessage(Customer customer, SMS sms) throws ApplicationException {
        /*add SMS provider*/
        sms.setSent(true);
        smsRepository.save(sms);
    }

    @Override
    public void sendGroupSMSMessage(Set<Customer> customer, SMS sms) throws ApplicationException {
        /*add SMS provider*/
        sms.setSent(true);
        smsRepository.save(sms);
    }

    @Override
    public void sendPersonalNotificationMessage(Rider rider, PushNotification notification) {
      this.sendNotificationToRider(notification, rider);
    }

    @Override
    public void sendGlobalNotificationMessage(PushNotification notification){
        notificationController.sendGlobalNotification(notificationMapper.mapNotification(notification));
        notification.setSent(true);
        pushNotificationRepository.save(notification);
    }


    @Override
    public void sendGroupNotificationMessage(Set<Rider> Rider, PushNotification notification) {
        Rider.parallelStream().forEach(rider -> {
            this.sendNotificationToRider(notification, rider);
        });
    }

    private void sendNotificationToRider(PushNotification notification, Rider rider) {
        final String sessionId = ActiveUserSessionComponent.getUserActiveSessionId(rider.getUsername());
        if (sessionId != null && !sessionId.isEmpty()) {
            notificationController.sendPersonalNotification(sessionId, notificationMapper.mapNotification(notification));
            notification.setSent(true);
            pushNotificationRepository.save(notification);
        }
    }

    @Scheduled(cron = "${pushNotification.config.jobDurationInMinutes}")
    private void PushNotificationJob(){
        List<PushNotification> notifications = pushNotificationRepository.findAllByIsSent(false);
        if(notifications!=null && !notifications.isEmpty()){
            notifications.stream().forEach(pushNotification ->{
                if(NotificationType.SINGLE.equals(pushNotification.getNotificationType()))
                    this.sendPersonalNotificationMessage(pushNotification.getRiders().iterator().next(), pushNotification);
                else if(NotificationType.GROUP.equals(pushNotification.getNotificationType()))
                    this.sendGroupNotificationMessage(pushNotification.getRiders(), pushNotification);
                else if(NotificationType.GLOBAL.equals(pushNotification.getNotificationType()))
                    this.sendGlobalNotificationMessage(pushNotification);
            });
        }
    }

    @Scheduled(cron = "${sms.config.jobDurationInMinutes}")
    private void sendSMSJob(){
        List<SMS> smses = smsRepository.findAllByIsSentOrderByCreatedAtAsc(false, PageRequest.of(0, maxNumOfSMSPerMinute));
        if(smses!=null && !smses.isEmpty()){
            smses.stream().forEach(sms ->{
               try{
                if(NotificationType.SINGLE.equals(sms.getSmsType()))
                    this.sendPersonalSMSMessage(sms.getCustomers().iterator().next(), sms);
                else if(NotificationType.GROUP.equals(sms.getSmsType()))
                    this.sendGroupSMSMessage(sms.getCustomers(), sms);
                }catch (Exception ex){
                   throw new RuntimeException(ex);
               }
            });
        }
    }
}
