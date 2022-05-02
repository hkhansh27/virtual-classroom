package com.virtualclassroom.controller;

import com.virtualclassroom.dto.NewsDto;
import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import com.virtualclassroom.service.classroom.ClassroomService;
import com.virtualclassroom.service.news.NewsService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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
    public String getNewsPage(@RequestParam (value = "pageId") int pageId,@RequestParam Long classroomId,Model model) {
        int pageSize = 4;
        List<NewsDto> newsDtoList = new ArrayList<>();
        Page<News> page = newsService.findPaginated(classroomService.getClassroomById(classroomId).getId(), pageId, pageSize);
        List<News> newsListView = page.getContent();
        newsListView.forEach(news -> {
            var teacherList = userService.findByRoleAndNews("TEACHER", news.getId());
            var studentList = userService.findByRoleAndNews("STUDENT", news.getId());
        newsDtoList.add(new NewsDto(
           news.getId(),
           news.getTitle(),
           news.getContent(),
           news.getTimestamp(),
           teacherList,
           studentList));
        });
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("newsDtoList", newsDtoList);
        model.addAttribute("currentPage", pageId);
        model.addAttribute("totalsPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
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
