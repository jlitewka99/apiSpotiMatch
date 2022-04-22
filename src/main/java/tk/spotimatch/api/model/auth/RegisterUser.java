package tk.spotimatch.api.model.auth;

import lombok.Data;

@Data
public class RegisterUser {

    private final String email;

    private final String password;

}
