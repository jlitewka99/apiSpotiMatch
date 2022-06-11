package tk.spotimatch.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.spotimatch.api.model.pairing.Pair;
import tk.spotimatch.api.model.user.User;
import tk.spotimatch.api.model.user.UserDTO;
import tk.spotimatch.api.service.PairFinderService;
import tk.spotimatch.api.service.PairService;
import tk.spotimatch.api.service.UserService;

@RestController
public class PairController {

    @Autowired
    PairService pairService;

    @Autowired
    UserService userService;

    @Autowired
    PairFinderService pairFinderService;

    @GetMapping("/pairs/find")
    public ResponseEntity<?> findPair() {
        return userService.findByEmail(
                        SecurityContextHolder.getContext().getAuthentication().getName())
                .map(this::findPair)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/pairs/{id}")
    public ResponseEntity<?> getPairById(@PathVariable Long id) {
        return ResponseEntity.of(pairService.findById(id));
    }

    @PostMapping("/pairs")
    public ResponseEntity<?> createPair(@RequestBody Pair pair) {
        return ResponseEntity.ok(pairService.save(pair));
    }

    public ResponseEntity<?> findPair(User user) {
        return ResponseEntity.of(pairFinderService.findPair(user).map(UserDTO::from));
    }
}
