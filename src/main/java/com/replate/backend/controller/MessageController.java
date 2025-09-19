package com.replate.backend.controller;

import com.replate.backend.dto.MessageDto;
import com.replate.backend.model.Message;
import com.replate.backend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> save(@RequestBody MessageDto messageDto) {
        Message message = messageService.saveMessage(messageDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam Long userA, @RequestParam Long userB) {
        return messageService.getConversation(userA, userB);
    }

    @GetMapping("/users")
    public List<Message> getUserConversations(@RequestParam Long userId) {
        return messageService.getUserConversations(userId);
    }

}
