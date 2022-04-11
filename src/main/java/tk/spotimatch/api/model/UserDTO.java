package tk.spotimatch.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getEmail());
    }

}
