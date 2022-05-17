package tk.spotimatch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.spotimatch.api.model.chat.ChatMessage;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

}
