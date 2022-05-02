package com.virtualclassroom.controller;

import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.homework.HomeworkService;
import com.virtualclassroom.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('TEACHER')")
@RequestMapping("/homework")
public class HomeworkController {
    private final ClassroomService classroomService;
    private final HomeworkService homeworkService;
    private final UserService userService;

    public HomeworkController(ClassroomService classroomService, HomeworkService homeworkService, UserService userService) {
        this.classroomService = classroomService;
        this.homeworkService = homeworkService;
        this.userService = userService;
    }

    @GetMapping()
    public String getHomework(@RequestParam(value = "classroomId") Long classroomId, Model model) {
        var homeworkList = homeworkService.findHomeworkByClassroomId(classroomId);
        model.addAttribute("homeworkList", homeworkList);
        return "homework-list";
    }

    @PostMapping()
    public String gradeHomework(@RequestParam(value = "homeworkId") Long homeworkId, @RequestParam(value = "score") Integer score, Model model) {
        homeworkService.findHomeworkById(homeworkId).ifPresent(homework -> {
            homework.setScore(score);
            homeworkService.saveHomework(homework);
        });
        return "redirect:/";
    }
}
