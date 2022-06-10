package tk.spotimatch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "preferences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Sex sex;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="preferences_music_genre_mapping",
            joinColumns = @JoinColumn(name = "preference_id"),
            inverseJoinColumns = @JoinColumn(name = "music_genre_id")
    )
    private Set<MusicGenre> musicGenres;

}
