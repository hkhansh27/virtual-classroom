package com.virtualclassroom.controller;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.news.NewsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final ClassroomService classroomService;
    public NewsController(NewsService newsService, ClassroomService classroomService){this.newsService = newsService;
        this.classroomService = classroomService;
    }

    @GetMapping()
    public String getNewsPage(@RequestParam String classroomId ,Model model) {
        //lay news cua 1 lop

        model.addAttribute("classroomId", classroomId);
        model.addAttribute("news", newsService.getAllNews());
        return "news";
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/add/{classroomId}")
    public String addNews(@PathVariable("classroomId") Long classroomId,Model model) {
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("news", new News());
        return "add-news";
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping()
    public String postAddNews(@RequestParam(value = "classroomId") Long classroomId,@NotNull News news, Classroom classroom) {
        classroom = classroomService.getClassroomById(classroomId);
        news.setClassrooms(classroom);
        newsService.addNews(news);
        return "add-news";
    }
}
