package tk.spotimatch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tk.spotimatch.api.model.user.MusicGenre;

public interface MusicGenresRepository extends JpaRepository<MusicGenre, Long> {
}
