package tk.spotimatch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tk.spotimatch.api.model.auth.RegisterUser;

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

    private String name;

    private Integer age;

    private String bio;

    private String picture;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User from(RegisterUser registerUser) {
        return new User(registerUser.getEmail(), registerUser.getPassword());
    }

    public static User from(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getEmail(), null, userDTO.getName(),
                userDTO.getAge(), userDTO.getBio(), userDTO.getPicture());
    }

    public void merge(UserDTO userDTO) {
        if (userDTO.getName() != null) this.name = userDTO.getName();
        if (userDTO.getAge() != null) this.age = userDTO.getAge();
        if (userDTO.getBio() != null) this.bio = userDTO.getBio();
        if (userDTO.getPicture() != null) this.picture = userDTO.getPicture();
    }

}
