package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.user.User;
import tk.spotimatch.api.model.user.UserDTO;
import tk.spotimatch.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(Long.parseLong(id));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository
                .findByEmailAllIgnoreCase(email)
                .stream()
                .findFirst();
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public Optional<User> update(String userEmail, UserDTO userDTO) {
        final var userToUpdate = findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException(userDTO.getEmail()));
        final var preferences = userDTO.getPreferences();
        if (userToUpdate.getPreferences() == null) {
            userToUpdate.setPreferences(preferences);
        } else if (preferences != null) {
            userToUpdate.getPreferences().merge(preferences);
        }
        userToUpdate.merge(userDTO);
        return Optional.of(userRepository.save(userToUpdate));
    }

}
