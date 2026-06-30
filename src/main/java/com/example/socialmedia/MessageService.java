package com.example.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public Message send(Message model){

        Message message = new Message();

        message.setSenderEmail(model.getSenderEmail());
        message.setReceiverEmail(model.getReceiverEmail());
        message.setMessage(model.getMessage());
        message.setTimestamp(LocalDateTime.now());
        message.setMessageType(model.getMessageType());
        message.setMediaUrl(model.getMediaUrl());
        message.setRead(false);

        User receiver = userRepository.findByEmail(model.getReceiverEmail());

        if(receiver != null && receiver.getFcmToken() != null){
            System.out.println("Receiver = " + receiver.getEmail());
            System.out.println("Token = " + receiver.getFcmToken());
            String notificationBody;

            if ("IMAGE".equals(model.getMessageType())) {
                notificationBody = "📷 Photo";
            } else {
                notificationBody = model.getMessage();
            }

            notificationService.sendNotification(
                    receiver.getFcmToken(),
                    model.getSenderEmail(),
                    notificationBody
            );
        }
        return messageRepository.save(message);
    }

    public List<Message> getChat(String user1, String user2){

        return messageRepository.getConversation(user1,user2);
    }
}
