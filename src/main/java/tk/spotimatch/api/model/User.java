package tk.spotimatch.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User from(RegisterUser registerUser) {
        return new User(registerUser.getEmail(), registerUser.getPassword());
    }

}
