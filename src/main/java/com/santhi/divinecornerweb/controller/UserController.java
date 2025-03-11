package com.santhi.divinecornerweb.controller;

import com.santhi.divinecornerweb.model.LoginRequest;
import com.santhi.divinecornerweb.model.Role;
import com.santhi.divinecornerweb.model.User;
import com.santhi.divinecornerweb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Show Registration Form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // ✅ Ensure a User object is passed
        return "register"; // ✅ Ensure this matches the correct Thymeleaf file name
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        userService.registerUser(user); // Save user in DB
        return "redirect:/users/login"; // Redirect to login after successful registration
    }


    // Show Login Form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


}