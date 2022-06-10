package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.user.MusicGenre;
import tk.spotimatch.api.repository.MusicGenresRepository;

@Service
public class MusicGenresService {
    @Autowired
    MusicGenresRepository musicGenresRepository;

    public MusicGenre save(MusicGenre musicGenres) {
        return musicGenresRepository.save(musicGenres);
    }
}
