package com.virtualclassroom.controller;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.User;
import com.virtualclassroom.repository.ClassRepository;
import com.virtualclassroom.repository.UserRepository;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Authenticator;
import java.util.Random;

@Controller
@RequestMapping("/classroom")
public class ClassController {
    private final UserService userService;
    private final ClassroomService classroomService;

    public ClassController(UserService userService, ClassroomService classroomService) {
        this.userService = userService;
        this.classroomService = classroomService;
    }
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    @PostMapping("/save")
    public String saveClassroom(Classroom classroom) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userService.findByUsername(authentication.getName());
            classroom.setCodeClass(getRandomNumberString());
            user.getClassrooms().add(classroom);
            userService.addUser(user);
        }
        //classroomService.createClass(classroom);
        return "classroom";
    }

    @GetMapping("/create")
    public String classroom(Model model) {
        model.addAttribute("classroom", new Classroom());
        return "classroom";
    }

}
