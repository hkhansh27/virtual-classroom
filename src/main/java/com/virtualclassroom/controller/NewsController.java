package com.virtualclassroom.controller;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.news.NewsService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService){this.newsService = newsService;}

    @GetMapping("/index")
    public String getNewsPage(Model model) {
        model.addAttribute("news", newsService.getAllNews());
        return "news";
    }

    @GetMapping("/home")
    public String home() {return "home";}


    @GetMapping("/add-news")
    public String addNews(@PathVariable("id") Long id, Model model) {
        //News news = newsService.get(id);
        model.addAttribute("news", new News());
        return "add-news";
    }

    @PostMapping
    public String addNews(@NotNull News news) {
        newsService.addNews(news);
        return "add-news";
    }
}
