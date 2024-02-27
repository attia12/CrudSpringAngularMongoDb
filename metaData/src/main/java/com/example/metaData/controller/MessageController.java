package com.example.metaData.controller;

import com.example.metaData.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;



@CrossOrigin("*")
@Controller
public class MessageController {
    private int notificationCount = 0;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Message send(final Message message) throws Exception {
        incrementNotificationCount();
        System.out.println(message);
        return message;
    }
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
    private void incrementNotificationCount() {
        notificationCount++;
        // Broadcast notification count update to all subscribed clients
        simpMessagingTemplate.convertAndSend("/topic/notificationCount", notificationCount);
    }
}
