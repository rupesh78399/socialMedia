package com.example.socialmedia;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String token , String senderName , String messageText){

        try {

            com.google.firebase.messaging.Message message = com.google.firebase.messaging.Message.builder()
                            .setToken(token)
                            .putData("title", senderName)
                            .putData("body", messageText)
                            .build();

            String response = FirebaseMessaging.getInstance().send(message);

            System.out.println("Notification Sent: " + response);

        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
