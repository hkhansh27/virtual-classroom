package com.virtualclassroom.controller;

import com.virtualclassroom.dto.NewsDto;
import com.virtualclassroom.model.News;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.news.NewsService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final ClassroomService classroomService;
    private final UserService userService;


    public NewsController(NewsService newsService, ClassroomService classroomService, UserService userService) {
        this.newsService = newsService;
        this.classroomService = classroomService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping()
    public String getNewsPage(@RequestParam Long classroomId,Model model, @NotNull News newss) {
        List<NewsDto> newsDtoList = new ArrayList<>();
        var newsList = newsService.getByClassId(classroomId);
        //var getNewsId = newsService.getNewsById(newss.getId());
        newsList.forEach(news -> {
            var teacherList = userService.findByRoleAndClassroom("TEACHER", news.getId());
            var studentList = userService.findByRoleAndClassroom("STUDENT", news.getId());
        newsDtoList.add(new NewsDto(
           news.getId(),
           news.getTitle(),
           news.getContent(),
           news.getTimestamp(),
           teacherList,
           studentList));
        });
        model.addAttribute("classroomId", classroomId);
        //model.addAttribute("newsId",getNewsId);
        model.addAttribute("newsDtoList", newsDtoList);
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
    public String postAddNews(@RequestParam(value = "classroomId") Long classroomId,@NotNull News news) {
        news.setTimestamp(new Date());
        news.setClassroom(classroomService.getClassroomById(classroomId));
        news.setUser(Collections.singleton(userService.getCurrentUser()));
        newsService.addNews(news);
        return "redirect:/news/add/" + classroomId;
    }

}
