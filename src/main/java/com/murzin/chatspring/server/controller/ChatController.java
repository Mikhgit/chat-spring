package com.murzin.chatspring.server.controller;

import com.murzin.chatspring.server.dbService.dataSet.Message;
import com.murzin.chatspring.server.dbService.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private MessageDAO messageDAO;
    
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(@Payload Message message) throws Exception {
        messageDAO.save(message);
        return message;
    }

    @MessageMapping("/history")
    @SendTo("/topic/messages")
    public Message getAllMessages(@Payload Message message) throws Exception {
        message.setMessages(messageDAO.findAll());
        return message;
    }

    @MessageMapping("/history/{username}")
    @SendTo("/topic/messages")
    public Message getAllMessageFromUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String[] arr = headerAccessor.getDestination().split("/");
        Iterable<Message> messages = messageDAO.findAllByUser(arr[arr.length - 1]);
        message.setMessages(messages);
        return message;
    }
}
