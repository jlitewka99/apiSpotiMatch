package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.pairing.Match;
import tk.spotimatch.api.model.pairing.Pair;
import tk.spotimatch.api.model.user.User;
import tk.spotimatch.api.repository.MatchesRepository;

import java.util.Objects;
import java.util.Optional;

import static tk.spotimatch.api.model.pairing.Match.State.*;
import static tk.spotimatch.api.model.pairing.Match.State.APPROVED;

@Service
public class MatchesService {
    @Autowired
    MatchesRepository matchesRepository;

    @Autowired
    PairService pairService;

    public boolean areSuitableForFinding(User firstUser, User secondUser) { // order is important
        return findMatchByUserId(firstUser.getId(), secondUser.getId())
                .map(m -> Objects.equals(m.getState(), NEW) && !Objects.equals(
                        firstUser.getId(), m.getLeftUserId().getId()))
                .orElse(true);
    }

    public Optional<Match> findMatchByUserId(Long firstUserId, Long secondUserId) {
        return matchesRepository.findAll().stream().filter(m ->
                        (Objects.equals(m.getLeftUserId().getId(), firstUserId) && Objects.equals(
                                m.getRightUserId().getId(), secondUserId) ||
                                (Objects.equals(m.getLeftUserId().getId(), secondUserId) && Objects.equals(
                                        m.getRightUserId().getId(), firstUserId))))
                .findFirst();
    }

    public Match save(Match match) {
        return matchesRepository.save(match);
    }

    public boolean matchTwoPositive(User firstUser, User secondUser) {
        return findMatchByUserId(firstUser.getId(), secondUser.getId())
                .map(m -> {
                    if (Objects.equals(m.getState(), NEW) && Objects.equals(
                            firstUser.getId(), m.getRightUserId().getId())) {
                        m.setState(APPROVED);
                        save(m);
                        Pair pair = new Pair();
                        pair.setLeftUserId(firstUser.getId());
                        pair.setLeftUserName(firstUser.getName());
                        pair.setRightUserId(secondUser.getId());
                        pair.setRightUserName(secondUser.getName());
                        pairService.save(pair);
                        return true;
                    } else {
                        return Objects.equals(m.getState(), APPROVED);
                    }
                })
                .orElseGet(() -> {
                    Match m = new Match();
                    m.setLeftUserId(firstUser);
                    m.setRightUserId(secondUser);
                    m.setState(NEW);
                    save(m);
                    return false;
                });
    }

    public void matchTwoNegative(User firstUser, User secondUser) {
        findMatchByUserId(firstUser.getId(), secondUser.getId())
                .ifPresentOrElse(m -> {
                    m.setState(NOT_APPROVED);
                    save(m);
                }, () -> {
                    Match m = new Match();
                    m.setLeftUserId(firstUser);
                    m.setRightUserId(firstUser);
                    m.setState(NOT_APPROVED);
                    save(m);
                });
    }
}
