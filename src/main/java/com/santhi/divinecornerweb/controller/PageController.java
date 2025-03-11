package com.santhi.divinecornerweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());
        return "home";
    }

    @GetMapping("/about")
    public String aboutUs() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactUs() {
        return "contact";
    }

}
