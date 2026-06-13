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

    public Message send(ChatMessage_Model model){

        Message message = new Message();
        message.setSenderEmail(model.getSender());
        message.setReceiverEmail((model.getReceiver()));
        message.setMessage(model.getContent());
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getChat(String user1, String user2){

        return messageRepository.getConversation(user1,user2);
    }
}
