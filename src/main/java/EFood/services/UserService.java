package EFood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EFood.models.User;
import EFood.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByID(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();

    }

    public User updateUser(Long id, User user) {
        var oldUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getPassword() != null) {
            oldUser.setPassword(user.getPassword());
        }
        if (user.getPhoneNumber() != null) {
            oldUser.setPhoneNumber(user.getPhoneNumber());

        }
        return userRepository.save(oldUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
