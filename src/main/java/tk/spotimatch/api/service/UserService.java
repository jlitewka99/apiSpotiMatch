package tk.spotimatch.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.spotimatch.api.model.User;
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

    public Optional<User> findByEmail(String email) {
        return userRepository
                .findByEmailAllIgnoreCase(email)
                .stream()
                .findFirst();
    }

    public User create(User user) {
        return userRepository
                .save(user);
    }

}
