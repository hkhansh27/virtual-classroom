package com.virtualclassroom.controller;

import com.virtualclassroom.dto.CommentDto;
import com.virtualclassroom.dto.NewsDto;
import com.virtualclassroom.model.Comment;
import com.virtualclassroom.model.User;
import com.virtualclassroom.service.comment.CommentService;
import com.virtualclassroom.service.news.NewsService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/news-details")
public class CommentController {
    private final CommentService commentService;
    private final NewsService newsService;
    private final UserService userService;

    public CommentController(CommentService commentService, NewsService newsService, UserService userService) {
        this.commentService = commentService;
        this.newsService = newsService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping()
    public String getNewsDetailsPage(@RequestParam Long newsId,Model model) {
        List<NewsDto> newsDetailsDtoList = new ArrayList<>();
        var newsDetailsList = newsService.getNewsById(newsId);
        newsDetailsList.forEach(news -> {
            var teacherList = userService.findByRoleAndClassroom("TEACHER", news.getId());
            var studentList = userService.findByRoleAndClassroom("STUDENT", news.getId());
            newsDetailsDtoList.add(new NewsDto(
                    news.getId(),
                    news.getTitle(),
                    news.getContent(),
                    news.getTimestamp(),
                    teacherList,
                    studentList));
        });
        model.addAttribute("newsId", newsId);
        model.addAttribute("newsDetailsDtoList", newsDetailsDtoList);
        return "news-details";
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping("/{newsId}")
    public String addCmt(@PathVariable("newsId") Long newsId, Model model, @NotNull Comment comment) {
        model.addAttribute("newsId", newsId);
        model.addAttribute("comment", new Comment());
        commentService.addComment(comment);
        return "news-details";
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @PostMapping()
    public String postAddComment(@RequestParam(value = "newsId") Long newsId,@NotNull Comment comment) {
        comment.setTimestamp(new Date());
        //comment.setNews(newsService.getNewsById(newsId));
        comment.setUser(userService.getCurrentUser());
        commentService.addComment(comment);
        return "redirect:/news-details/" + newsId;
    }
}
