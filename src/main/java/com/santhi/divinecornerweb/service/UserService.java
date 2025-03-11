package com.santhi.divinecornerweb.service;

import com.santhi.divinecornerweb.model.Role;
import com.santhi.divinecornerweb.model.User;
import com.santhi.divinecornerweb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public void registerUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already in use!");
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign default role if null
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
    }

    // Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
  //  Fetch all users (NEW method)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
