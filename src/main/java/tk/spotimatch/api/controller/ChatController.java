package tk.spotimatch.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tk.spotimatch.api.model.chat.ChatMessage;
import tk.spotimatch.api.model.chat.ChatNotification;
import tk.spotimatch.api.service.ChatService;
import tk.spotimatch.api.service.UserService;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage message) {
        log.info("test");
//        final var sender = userService.findById(message.getSenderId()).orElse(null);
//        if (sender == null) {
//            return;
//        }
//
//        final var recipient = userService.findById(message.getRecipientId()).orElse(null);
//        if (recipient == null) {
//            return;
//        }
//
//        if (message.getContent() == null || message.getContent().isBlank()) {
//            return;
//        }

        chatService.create(message);

        simpMessagingTemplate.convertAndSendToUser(message.getRecipientId(), "/queue/messages", new ChatNotification(Long.valueOf(message.getSenderId()), "xyz", message.getContent()));
    }

}
