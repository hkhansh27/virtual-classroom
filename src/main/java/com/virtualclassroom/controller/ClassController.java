package com.virtualclassroom.controller;

import com.virtualclassroom.dto.ClassrooomDto;
import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.user.UserService;
import com.virtualclassroom.utils.Helper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
        return "classroom";
    }

    @PostMapping("/join")
    public String joinClassroom(@RequestParam String keyword, Classroom classroom) {
            User user = userService.getCurrentUser();
            classroom = classroomService.findClassByCodeID(keyword);
            user.getClassrooms().add(classroom);
            userService.addUser(user);
            return "classroom";
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @GetMapping("/list")
    public String listClassroom(Model model) {
        List<ClassrooomDto> classrooomDtoList = new ArrayList<>();
        List<Classroom> classroomList = classroomService.getClassesByUsername(userService.getCurrentUser().getUserName());
        classroomList.forEach(classroom -> {
            var teacherList = userService.findByRoleAndClassroom("TEACHER", classroom.getId());
            var studentList = userService.findByRoleAndClassroom("STUDENT", classroom.getId());
            classrooomDtoList.add(new ClassrooomDto(
                    classroom.getId(),
                    classroom.getNameClass(),
                    classroom.getDescriptionClass(),
                    classroom.getCodeClass(),
                    teacherList,
                    studentList,
                    studentList.size()));
        });
        model.addAttribute("classroomDtoList", classrooomDtoList);
        return "course-list";
    }
}
