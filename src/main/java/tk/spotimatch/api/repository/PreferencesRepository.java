package tk.spotimatch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.spotimatch.api.model.user.Preference;

@Repository
public interface PreferencesRepository extends JpaRepository<Preference, Long> {
}
