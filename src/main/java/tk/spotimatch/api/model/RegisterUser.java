package tk.spotimatch.api.model;

import lombok.Data;

@Data
public class RegisterUser {

    private final String email;

    private final String password;

}
