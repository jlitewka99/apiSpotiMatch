package tk.spotimatch.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import tk.spotimatch.api.filters.JwtRequestFilter;
import tk.spotimatch.api.model.chat.ChatMessage;
import tk.spotimatch.api.model.chat.ChatNotification;
import tk.spotimatch.api.model.pairing.Pair;
import tk.spotimatch.api.model.user.User;
import tk.spotimatch.api.service.ChatService;
import tk.spotimatch.api.service.PairService;
import tk.spotimatch.api.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @Autowired
    PairService pairService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message<?> messageStomp) throws IOException {
        log.info("inside processMessage");

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(messageStomp, StompHeaderAccessor.class);

        log.info(accessor.getNativeHeader("Authorization").toString());

        assert accessor != null;

        List<String> authorization = accessor.getNativeHeader("Authorization");

        if (authorization == null || authorization.isEmpty()) {
            return;
        }

        final var user = jwtRequestFilter.getUserDetailsFromJwt(jwtRequestFilter.fetchJwt(authorization.get(0))).orElse(null);

        if (user == null) {
            log.info(MessageFormat.format("User for jwt: {0} not found", authorization.get(0)));
            return;
        }

//        final var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final var message = objectMapper.readValue(
                (byte[]) messageStomp.getPayload(), ChatMessage.class);

        userService.findByEmail(user.getUsername())
                .ifPresent(u -> doProcessMessage(message, u));

    }

    private void doProcessMessage(ChatMessage message, User sender) {
        userService.findById(message.getRecipientId())
                .ifPresent(recipient -> doProcessMessage(message, sender, recipient));
    }

    private void doProcessMessage(ChatMessage message, User sender, User recipient) {
        pairService.getPair(sender, recipient)
                .ifPresent(pair -> doProcessMessage(message, sender, recipient, pair));

    }

    private void doProcessMessage(ChatMessage message, User sender, User recipient, Pair pair) {
        if (message.getContent() == null || message.getContent().isBlank()) {
            return;
        }

        message.setRecipientId(recipient.getId());
        message.setSenderId(sender.getId());
        message.setTimestamp(Instant.now());
        message.setPairId(pair.getId());

        chatService.create(message);

        simpMessagingTemplate.convertAndSendToUser(
                message.getRecipientId().toString(), "/queue/messages",
                new ChatNotification(message.getSenderId(), sender.getName(),
                        message.getContent()));
    }

}
