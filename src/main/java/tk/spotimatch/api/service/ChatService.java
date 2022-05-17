package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.chat.ChatMessage;
import tk.spotimatch.api.repository.ChatRepository;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    public ChatMessage create(ChatMessage chatMessage) {
        return chatRepository.save(chatMessage);
    }

}
