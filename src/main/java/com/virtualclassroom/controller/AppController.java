package com.virtualclassroom.controller;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.Homework;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.homework.HomeworkService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AppController {
    private final UserService userService;
    private final HomeworkService homeworkService;

    public AppController(UserService userService, HomeworkService homeworkService) {
        this.userService = userService;
        this.homeworkService = homeworkService;
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

    @GetMapping("/upload")
    public String upload(Model model) {
        model.addAttribute("homework", new Homework());
        return "file-upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model, Homework homework) {
        if (file.isEmpty()) {
            model.addAttribute("success", "Failed");
            return "file-upload";
        }
        try {
            String fileName = file.getOriginalFilename();
            homework.setName(fileName);
            homework.setContent(file.getBytes());
            homework.setSize(file.getSize());
            Classroom classroom = new Classroom();
            homework.setClassrooms(classroom);
            homeworkService.createHomework(homework);
            model.addAttribute("success", "Created homework successfully!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("success", "File uploaded successfully");
        return "file-upload";
    }


    @PostMapping("/process_homework")
    public String processHomework(@NotNull Homework homework) {
        homeworkService.createHomework(homework);
        return "homework-list";
    }

    @GetMapping("/homework_list")
    public String getHomeworkListPage(Model model) {
        model.addAttribute("homeworkList", homeworkService.getAllHomework());
        return "homework-list";
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

//    @PostMapping("/upload")
//    public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
//        Homework homework = new Homework();
//        String fileName = file.getOriginalFilename();
//        homework.setProfilePicture(fileName);
//        homework.setContent(file.getBytes());
//        homework.setSize(file.getSize());
//        homeworkService.createHomework(homework);
//        model.addAttribute("success", "Created homework successfully!!!");
//        return "file";
//    }
//
//    @GetMapping("/downloadfile")
//    public void downloadFile(@Param("id") Long id , Model model, HttpServletResponse response) throws IOException {
//        Optional<Homework> temp = homeworkService.findHomeworkById(id);
//        if (temp.isPresent()) {
//            Homework homework = temp.get();
//            response.setContentType("application/octet-stream");
//            String headerKey = "Content-Disposition";
//            String headerValue = "attachment; filename = "+homework.getProfilePicture();
//            response.setHeader(headerKey, headerValue);
//            ServletOutputStream outputStream = response.getOutputStream();
//            outputStream.write(homework.getContent());
//            outputStream.close();
//        }
//    }
//
//    @GetMapping("/image")
//    public void showImage(@Param("id") Long id, HttpServletResponse response, Optional<Homework> homework)
//            throws ServletException, IOException {
//
//        homework = homeworkService.findHomeworkById(id);
//
//        if (homework.isPresent()) {
//            response.setContentType("image/png");
//            response.setContentType("image/jpeg");
//            response.setContentType("image/jpg");
//            response.getOutputStream().write(homework.get().getContent());
//            response.getOutputStream().close();
//        }
//
//    }
    }
}
