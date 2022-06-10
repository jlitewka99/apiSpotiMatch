package tk.spotimatch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private Sex sex;

    private Integer age;

    private String bio;

    private String picture;

    @JsonIgnore
    private Preference preferences;

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSex(),
                user.getAge(),
                user.getBio(),
                user.getPicture(),
                user.getPreferences()
        );
    }

}
