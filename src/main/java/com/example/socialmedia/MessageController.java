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
    @GetMapping("/allMessages")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
