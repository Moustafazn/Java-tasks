package com.notification.task.controller;

import com.notification.task.dto.LitePushNotificationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @ApiOperation(value = "send message to customer")
    @ApiResponses({@ApiResponse(code = 1 , message = "message sent successfully", response = String.class),
            @ApiResponse(code = 1 , message = "Unexpected error happened")})
    @MessageMapping("/customerMessage")
    @SendToUser("/queue/customerReply")
    public String sendGreetingCustomerMessage(@Payload String message, Principal principal) throws Exception {
        return "Hello Customer " + principal.getName();
    }

    @ApiOperation(value = "send message to rider")
    @ApiResponses({@ApiResponse(code = 1 , message = "message sent successfully", response = String.class),
            @ApiResponse(code = 1 , message = "Unexpected error happened")})
    @MessageMapping("/riderMessage")
    @SendToUser("/queue/reply")
    public String sendGreetingRiderMessage(@Payload String message, Principal principal) throws Exception {
        return "Hello Rider " + principal.getName();
    }

    public void sendPersonalNotification(String riderId, LitePushNotificationDto notification) {
        simpMessagingTemplate.convertAndSendToUser(riderId, "/queue/personalNotification",notification);
    }

    public void sendGlobalNotification(LitePushNotificationDto notification) {
        simpMessagingTemplate.convertAndSend("/topic/globalNotification",notification);
    }

}
