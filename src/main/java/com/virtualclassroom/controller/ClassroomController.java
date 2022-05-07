package com.virtualclassroom.controller;

import com.virtualclassroom.dto.ClassrooomDto;
import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.Homework;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.homework.HomeworkService;
import com.virtualclassroom.service.news.NewsService;
import com.virtualclassroom.service.user.UserService;
import com.virtualclassroom.utils.Helper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {
    private final UserService userService;
    private final ClassroomService classroomService;
    private final HomeworkService homeworkService;
    private final NewsService newsService;

    public ClassroomController(UserService userService, ClassroomService classroomService, HomeworkService homeworkService, NewsService newsService) {
        this.userService = userService;
        this.classroomService = classroomService;
        this.homeworkService = homeworkService;
        this.newsService = newsService;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @PostMapping("/save")
    public String saveClassroom(Classroom classroom) {
        User user = userService.getCurrentUser();
        classroom.setCodeClass(Helper.getRandomNumberString());
        user.getClassrooms().add(classroom);
        userService.addUser(user);
        return "redirect:/classroom";
    }

    @PostMapping("/join")
    public String joinClassroom(@RequestParam String keyword, Classroom classroom) {
        User user = userService.getCurrentUser();
        classroom = classroomService.findClassByCodeID(keyword);
        user.getClassrooms().add(classroom);
        userService.addUser(user);
        return "redirect:/classroom/" + classroom.getId();
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping()
    public String listClassroom(@RequestParam (value = "pageId", defaultValue = "1") int pageId, Model model) {
        int pageSize = 6;
        List<ClassrooomDto> classroomDtoList = new ArrayList<>();
        Page<Classroom> page = classroomService.findPaginated(userService.getCurrentUser().getUserName(), pageId, pageSize);
        List<Classroom> classroomsListView = page.getContent();
        classroomsListView.forEach(classroom -> {
            var teacherList = userService.findByRoleAndClassroom("TEACHER", classroom.getId());
            var studentList = userService.findByRoleAndClassroom("STUDENT", classroom.getId());
            classroomDtoList.add(new ClassrooomDto(
                    classroom.getId(),
                    classroom.getNameClass(),
                    classroom.getDescriptionClass(),
                    classroom.getCodeClass(),
                    teacherList,
                    studentList,
                    studentList.size()));
        });
        model.addAttribute("classroomDtoList", classroomDtoList);
        model.addAttribute("currentPage", pageId);
        model.addAttribute("totalsPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("newClassroom", new Classroom());
        return "course-list";
    }

    @GetMapping("/{classroomId}")
    public String getCourseDetails(@PathVariable("classroomId") Long classroomId, Model model) {
        var homeworkList = homeworkService.getHomeworkByClassIdAndUsername(classroomId, userService.getCurrentUser().getUserName());
        var homeworkTeacherList = homeworkService.findHomeworkByTeacher(classroomId);
        var classroomDetails = classroomService.getClassroomById(classroomId);
        var newsInClass = newsService.getByClassId(classroomId);
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("homeworkObj", new Homework());
        model.addAttribute("homeworkList", homeworkList);
        model.addAttribute("homeworkTeacherList", homeworkTeacherList);
        model.addAttribute("classroomDetails", classroomDetails);
        model.addAttribute("newsInClass", newsInClass);
        return "course-details";
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @PostMapping()
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam(name = "classroomId") Long classroomId, Model model, Homework homework, RedirectAttributes redirAttrs) {
        if (file.isEmpty()) {
            redirAttrs.addFlashAttribute("error", "Failed to store empty file");
            return "redirect:/classroom/" + classroomId;
        }
        try {
//          String fileName = file.getOriginalFilename();
            homework.setName(homework.getName());
            homework.setContent(file.getBytes());
            homework.setSize(file.getSize());
            var classroom = classroomService.getClassroomById(classroomId);
            homework.addUser(userService.getCurrentUser());
            homework.setClassroom(classroom);
            homeworkService.createHomework(homework);
            redirAttrs.addFlashAttribute("success", "Created homework successfully!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        redirAttrs.addFlashAttribute("success", "File uploaded successfully");
        return "redirect:/classroom/" + classroomId;
    }
}
