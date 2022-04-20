package com.virtualclassroom.controller;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.user.UserService;
import com.virtualclassroom.utils.Helper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classroom")
public class ClassController {
    private final UserService userService;
    private final ClassroomService classroomService;

    public ClassController(UserService userService, ClassroomService classroomService) {
        this.userService = userService;
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/create")
    public String classroom(Model model) {
        model.addAttribute("classroom", new Classroom());
        return "classroom";
    }

    @PostMapping("/save")
    public String saveClassroom(Classroom classroom) {
        User user = userService.getCurrentUser();
        classroom.setCodeClass(Helper.getRandomNumberString());
        user.getClassrooms().add(classroom);
        userService.addUser(user);
        classroomService.createClass(classroom);
        return "classroom";
    }
}
