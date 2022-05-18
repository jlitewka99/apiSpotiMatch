package tk.spotimatch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.spotimatch.api.model.pairing.Pair;

import java.util.List;

@Repository
public interface PairRepository extends JpaRepository<Pair, Long> {

    List<Pair> findByLeftUserIdAndRightUserId(Long leftUserId, Long rightUserId);

    List<Pair> findByLeftUserIdOrRightUserId(Long leftUserId, Long rightUserId);
}
