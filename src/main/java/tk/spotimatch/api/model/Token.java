package tk.spotimatch.api.model;

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
