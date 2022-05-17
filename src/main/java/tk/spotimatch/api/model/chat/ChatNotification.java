package tk.spotimatch.api.model.chat;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatNotification {
    private Long senderId;

    private String senderName;

    private String content;
}
