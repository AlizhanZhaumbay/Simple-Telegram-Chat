package com.example.messagingstompwebsocket;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.example.messagingstompwebsocket.ChatDestinations.*;

@Controller
@AllArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping(FETCH_PUBLIC_MESSAGES)
    public void sendMessage(Principal principal, @Payload Message message) {
        message.setFrom(principal.getName());
        messagingTemplate.convertAndSend(SEND_MESSAGE_TO_ALL, message);
    }

    @MessageMapping(FETCH_PRIVATE_MESSAGES)
    public ResponseEntity<Message> sendPrivateMessage(Principal principal, Message message) {
        message.setFrom(principal.getName());
        messagingTemplate.convertAndSendToUser(message.getRecipient(),
                SEND_MESSAGE_TO_SPECIFIC,
                message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
