package tk.spotimatch.api.model.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private final String email;

    private final String password;

}
