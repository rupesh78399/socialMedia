package com.example.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    MessageService service;
    @Autowired
    MessageRepository messageRepository;

    @PostMapping("/send")
    public Message send(@RequestBody Message request){

        return service.send(request);
    }

    @GetMapping("/chat")
    public List<Message> getChat(@RequestParam String user1, @RequestParam String user2){

        return service.getChat(user1,user2);
    }
    @GetMapping("/allMessage")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @PutMapping("/read")
    public void markAsRead(@RequestParam String sender , @RequestParam String receiver){

        List<Message> messages = messageRepository.findUnreadMessages(sender , receiver);

        for(Message m : messages){
            m.setRead(true);
        }
        messageRepository.saveAll(messages);
    }

    @GetMapping("/unreadCount")
    public Long getUnreadCount(@RequestParam String sender , @RequestParam String receiver){

        return messageRepository.countUnreadMessage(sender , receiver);
    }
}
