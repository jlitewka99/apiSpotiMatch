package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.pairing.Pair;
import tk.spotimatch.api.model.user.User;
import tk.spotimatch.api.repository.PairRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PairService {

    @Autowired
    PairRepository pairRepository;

    @Autowired
    UserService userService;

    public Optional<Pair> findById(Long id) {
        return pairRepository.findById(id);
    }

    public Pair save(Pair pair) {
        return pairRepository.save(pair);
    }

    public boolean areInPair(User leftUser, User rightUser) {
        return getPair(leftUser, rightUser).isPresent();
    }

    public Optional<Pair> getPair(Long leftUserId, Long rightUserId) {
        return userService.findById(leftUserId).flatMap(leftUser -> getPair(leftUser, rightUserId));
    }

    public Optional<Pair> getPair(User leftUser, Long rightUserId) {
        return userService.findById(rightUserId)
                .flatMap(rightUser -> getPair(leftUser, rightUser));
    }

    public Optional<Pair> getPair(User leftUser, User rightUser) {
        if (leftUser == null || rightUser == null) {
            return Optional.empty();
        }

        final var firstResult = pairRepository.findByLeftUserIdAndRightUserId(
                leftUser.getId(), rightUser.getId());

        if (!firstResult.isEmpty()) {
            return Optional.of(firstResult.get(0));
        }

        final var secondResult = pairRepository.findByLeftUserIdAndRightUserId(
                rightUser.getId(), leftUser.getId());

        return secondResult.stream().findFirst();
    }

    public List<Pair> pairsForUser(String email) {
        if (email == null) {
            return new ArrayList<>();
        }

        return userService.findByEmail(email)
                .map(this::pairsForUser)
                .orElseGet(ArrayList::new);
    }

    public List<Pair> pairsForUser(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        return userService.findById(userId)
                .map(this::pairsForUser)
                .orElseGet(ArrayList::new);
    }

    public List<Pair> pairsForUser(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        return pairRepository.findByLeftUserIdOrRightUserId(user.getId(), user.getId());
    }

}
