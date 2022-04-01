package com.virtualclassroom.controller;

import com.virtualclassroom.model.User;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    private final UserService userService;

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "login-register";
    }

    @PostMapping("/process_register")
    public String processRegister(@NotNull User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        userService.addUser(user);
        return "login-register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "login-register";
//        }
//        return "redirect:/";
        return "login-register";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("loginError", true);
        return "login-register";
    }
}
