package com.replate.backend.service;

import com.replate.backend.dto.MessageDto;
import com.replate.backend.model.Message;
import com.replate.backend.model.User;
import com.replate.backend.repository.MessageRepository;
import com.replate.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message saveMessage(MessageDto messageDto) {
        User sender = userRepository.findById(messageDto.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(messageDto.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message message = new Message(sender, receiver, messageDto.getText());
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long userA, Long userB) {
        return messageRepository.findConversationBetweenUsers(userA, userB);
    }

    public List<Message> getUserConversations(Long user) {
        return messageRepository.findAllBySenderIdOrReceiverId(user, user);
    }
}
