package tk.spotimatch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import tk.spotimatch.api.model.auth.RegisterUser;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    private String name;

    private Sex sex;

    private Integer age;

    private String bio;

    private String picture;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "preference_id")
    private Preference preferences;

    @CreationTimestamp
    private Instant timestamp;

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User from(RegisterUser registerUser) {
        return new User(registerUser.getEmail(), registerUser.getPassword());
    }

    public static User from(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getEmail(), null, userDTO.getName(),
                userDTO.getSex(), userDTO.getAge(), userDTO.getBio(), userDTO.getPicture(),
                userDTO.getPreferences(), null);
    }

    public void merge(UserDTO userDTO) {
        if (userDTO.getName() != null) {
            this.name = userDTO.getName();
        }
        if (userDTO.getAge() != null) {
            this.age = userDTO.getAge();
        }
        if (userDTO.getBio() != null) {
            this.bio = userDTO.getBio();
        }
        if (userDTO.getSex() != null) {
            this.sex = userDTO.getSex();
        }
        if (userDTO.getPicture() != null) {
            this.picture = userDTO.getPicture();
        }
        if (userDTO.getPreferences() != null) {
            this.preferences = userDTO.getPreferences();
        }
    }

    public boolean match(User secondUser) {
        final var secondUserPreferences = secondUser.getPreferences();
        return preferences != null && secondUserPreferences != null &&
                sexPreferencesMatchesWith(secondUser) &&
                musicPreferencesMatchesWith(secondUser);
    }

    private boolean sexPreferencesMatchesWith(User secondUser) {
        final var secondUserPreferences = secondUser.getPreferences();
        return Objects.equals(preferences.getSex(), secondUser.getSex()) &&
                Objects.equals(getSex(), secondUserPreferences.getSex());
    }

    private boolean musicPreferencesMatchesWith(User secondUser) {
        final var secondUserPreferences = secondUser.getPreferences();
        return secondUserPreferences.getMusicGenres().stream()
                .anyMatch(m -> preferences.getMusicGenres().stream()
                        .anyMatch(k -> Objects.equals(m.getGenre(), k.getGenre())));
    }

}
