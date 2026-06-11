package com.example.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.LocalDateTime;

public class WebSocket_Controller {

    @Autowired
    MessageRepository repository;

    @MessageMapping("/send")
    @SendTo("/topic/message")
    public ChatMessage_Model send(ChatMessage_Model message){

        Message msg = new Message();

        msg.setSenderEmail(message.getSender());
        msg.setReceiverEmail(message.getReceiver());
        msg.setMessage(message.getContent());
        msg.setTimestamp(LocalDateTime.now());

        repository.save(msg);
        return message;

    }
}
