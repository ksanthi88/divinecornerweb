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

//    @PostMapping("/register")
//    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
//        System.out.println("📩 Received Registration Request: " + user);
//
//        if (result.hasErrors()) {
//            model.addAttribute("errorMessage", "Validation failed! Please check the inputs.");
//            return "register"; // Show registration form again with errors
//        }
//
//        try {
//            userService.registerUser(user);
//            System.out.println("✅ User registered successfully: " + user);
//        } catch (RuntimeException e) {
//            System.err.println("❌ Registration error: " + e.getMessage());
//            model.addAttribute("errorMessage", e.getMessage());
//            return "register";
//        }
//
//        return "redirect:/users/login"; // ✅ Ensure this matches the Thymeleaf login page
//    }
    // Show Login Form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }


}