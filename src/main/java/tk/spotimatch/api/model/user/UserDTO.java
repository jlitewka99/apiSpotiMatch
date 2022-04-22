package tk.spotimatch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private Integer age;

    private String bio;

    private String picture;

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getAge(),
                user.getBio(),
                user.getPicture()
        );
    }

}
