package tk.spotimatch.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.spotimatch.api.model.user.UserDTO;
import tk.spotimatch.api.service.UserService;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.of(userService.findByEmail(email));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserDTO userDTO) {
        var currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.of(userService.update(currentUserId, userDTO));
    }
}
