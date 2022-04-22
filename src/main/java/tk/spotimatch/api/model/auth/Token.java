package tk.spotimatch.api.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Token {

    private String username;

    private Long userId;

    public static Token from(String token) {
        return new Token(token, 123L);
    }
}
