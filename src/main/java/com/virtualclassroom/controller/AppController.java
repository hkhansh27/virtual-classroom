package com.virtualclassroom.controller;

import com.virtualclassroom.model.Homework;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.homework.HomeworkService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.virtualclassroom.model.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@Controller
public class AppController {
    private final UserService userService;
    private final HomeworkService homeworkService;
    private final ClassroomService classroomService;

    public AppController(UserService userService, HomeworkService homeworkService, ClassroomService classroomService) {
        this.userService = userService;
        this.homeworkService = homeworkService;
        this.classroomService = classroomService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "login-register";
    }

    @PostMapping("/process_register")
    public String processRegister(@NotNull User user) {
        userService.saveUserWithDefaultRole(user);
        return "login-register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        if(userService.getCurrentUser() == null) {
            return "login-register";
        }
        return "redirect:/";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("loginError", true);
        return "login-register";
    }

    @GetMapping("/homework_list/download")
    public void download(@Param("id") Long id, HttpServletResponse response) throws IOException, ServletException {
        Optional<Homework> optionalHomework = homeworkService.findHomeworkById(id);
        if (optionalHomework.isPresent()) {
            Homework homework = optionalHomework.get();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + homework.getName() + "\"");
            response.getOutputStream().write(homework.getContent());
        }
    }
}
