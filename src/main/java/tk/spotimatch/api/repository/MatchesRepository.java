package tk.spotimatch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.spotimatch.api.model.pairing.Match;

@Repository
public interface MatchesRepository extends JpaRepository<Match, Long> {
}
