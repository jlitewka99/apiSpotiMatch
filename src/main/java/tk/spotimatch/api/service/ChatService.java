package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.chat.ChatMessage;
import tk.spotimatch.api.model.pairing.Pair;
import tk.spotimatch.api.repository.ChatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    PairService pairService;

    public ChatMessage create(ChatMessage chatMessage) {
        return chatRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesForPair(Long leftUserId, Long rightUserId) {
        return pairService.getPair(leftUserId, rightUserId)
                .map(this::getMessagesForPair)
                .orElseGet(ArrayList::new);
    }

    public List<ChatMessage> getMessagesForPair(Pair pair) {
        if (pair == null) {
            return new ArrayList<>();
        }
        return chatRepository.findByPairId(pair.getId());
    }

}
