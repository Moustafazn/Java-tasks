package com.notification.task.service.mapper;

import com.notification.task.dto.LitePushNotificationDto;
import com.notification.task.model.PushNotification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class );

    LitePushNotificationDto mapNotification(PushNotification notification);

}
