package tk.spotimatch.api.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name = "music_genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Transactional
public class MusicGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String genre;

}
