package tk.spotimatch.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.spotimatch.api.model.user.User;
import tk.spotimatch.api.model.user.UserDTO;
import tk.spotimatch.api.service.ChatService;
import tk.spotimatch.api.service.MatchesService;
import tk.spotimatch.api.service.PairService;
import tk.spotimatch.api.service.UserService;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PairService pairService;

    @Autowired
    ChatService chatService;

    @Autowired
    MatchesService matchesService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.findById(id).map(UserDTO::from));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.of(userService.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody UserDTO userDTO) {
        final var currentUserName = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return ResponseEntity.of(userService.update(currentUserName, userDTO));
    }

    @GetMapping("/me/pairs")
    public ResponseEntity<?> listPairsForUserId() {
        final var currentUserName = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return ResponseEntity.ok(pairService.pairsForUser(currentUserName));
    }

    @GetMapping("/me/messages/{userId}")
    public ResponseEntity<?> listMessagesWithUserId(@PathVariable Long userId) {
        final var currentUserName = SecurityContextHolder.getContext().getAuthentication()
                .getName();

        return ResponseEntity.ok(userService.findByEmail(currentUserName)
                .map(leftUser -> chatService.getMessagesForPair(leftUser.getId(), userId))
                .orElseGet(ArrayList::new));
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> matchWithUser(@PathVariable Long userId, @RequestParam boolean match) {
        return userService.findById(userId)
                .map(u -> matchWithUser(u, match))
                .orElse(ResponseEntity.badRequest().build());
    }

    public ResponseEntity<?> matchWithUser(User user, boolean match) {
        final var currentUserName = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        return userService.findByEmail(currentUserName)
                .map(u -> matchWithUser(u, user, match))
                .orElse(ResponseEntity.badRequest().build());
    }

    public ResponseEntity<?> matchWithUser(User currentUser, User userToMatch, boolean match) {
        if (match) {
           return ResponseEntity.ok(matchesService.matchTwoPositive(currentUser, userToMatch));
        }
        matchesService.matchTwoNegative(currentUser, userToMatch);
        return ResponseEntity.ok().build();
    }

}
