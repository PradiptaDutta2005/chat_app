package com.pradipta.chat_app.controller;

import com.pradipta.chat_app.entity.Message;
import com.pradipta.chat_app.model.ChatMessage;
import com.pradipta.chat_app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository repo;

    @MessageMapping("/private-message")
    @SendTo("/topic/messages")
    public ChatMessage sendPrivateMessage(@Payload ChatMessage message) {

        Message msg = new Message();
        msg.setSender(message.getSender());
        msg.setReceiver(message.getReceiver());
        msg.setContent(message.getContent());
        msg.setTimestamp(LocalDateTime.now());

        repo.save(msg);   // 💾 SAVE TO DB

        return message;
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<Message> getChat(
            @RequestParam String user1,
            @RequestParam String user2) {

        List<Message> sent = repo.findBySenderAndReceiver(user1, user2);
        List<Message> received = repo.findBySenderAndReceiver(user2, user1);

        sent.addAll(received);

        sent.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        return sent;
    }
}