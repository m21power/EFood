package EFood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import EFood.models.UserModel;
import EFood.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserModel registerAdmin(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> getUserByID(Long id) {
        return userRepository.findById(id);
    }

    public List<UserModel> getAllUser() {
        return userRepository.findAll();

    }

    public UserModel updateUser(Long id, UserModel user) {
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
        if (user.getLogoUrl() != "") {
            oldUser.setLogoUrl(user.getLogoUrl());
        }
        return userRepository.save(oldUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserModel> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

}
