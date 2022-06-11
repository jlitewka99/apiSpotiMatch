package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.user.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PairFinderService {

    @Autowired
    UserService userService;

    @Autowired
    PairService pairService;

    @Autowired
    MatchesService matchesService;

    public List<User> findUsersNotMatchedWith(User user) {
        return userService.list().stream()
                .filter(u -> matchesService.areSuitableForFinding(user, u))
                .filter(u -> !Objects.equals(u.getId(), user.getId()))
                .collect(Collectors.toList());
    }

    public Optional<User> findPair(User user) {
        return findUsersNotMatchedWith(user)
                .stream()
                .filter(user::match)
                .findFirst();
    }
}
