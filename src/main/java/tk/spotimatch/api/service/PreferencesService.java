package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.user.Preference;
import tk.spotimatch.api.repository.PreferencesRepository;

import java.util.stream.Collectors;

@Service
public class PreferencesService {
    @Autowired
    PreferencesRepository preferencesRepository;

    @Autowired
    MusicGenresService musicGenresService;

    public Preference save(Preference preference) {
        if (preference.getMusicGenres() != null) {
            preference.setMusicGenres(
                    preference.getMusicGenres()
                            .stream()
                            .map(mg -> musicGenresService.save(mg))
                            .collect(Collectors.toSet())
            );
        }
        return preferencesRepository.save(preference);
    }
}
