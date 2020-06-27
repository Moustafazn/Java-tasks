package com.notification.task.service.mapper;

import com.notification.task.dto.LitePushNotificationDto;
import com.notification.task.model.PushNotification;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-05-09T21:31:06+0200",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 12.0.2 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public LitePushNotificationDto mapNotification(PushNotification notification) {
        if ( notification == null ) {
            return null;
        }

        LitePushNotificationDto litePushNotificationDto = new LitePushNotificationDto();

        litePushNotificationDto.setContent( notification.getContent() );

        return litePushNotificationDto;
    }
}
