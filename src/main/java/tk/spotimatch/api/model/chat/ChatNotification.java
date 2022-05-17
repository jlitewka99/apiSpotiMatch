package tk.spotimatch.api.model.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatNotification {
    private Long senderId;

    private String senderName;

    private String content;
}
