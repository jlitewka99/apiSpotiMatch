package tk.spotimatch.api.model;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private final String username;

    private final String password;

}
