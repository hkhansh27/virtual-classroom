package com.virtualclassroom.controller;

import com.virtualclassroom.dto.CommentDto;
import com.virtualclassroom.dto.NewsDto;
import com.virtualclassroom.model.Comment;
import com.virtualclassroom.service.comment.CommentService;
import com.virtualclassroom.service.news.NewsService;
import com.virtualclassroom.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String getNewsDetailsPage(@RequestParam (value = "pageId") int pageId,@RequestParam Long newsId,Model model) {
        int pageSize = 2;
        List<NewsDto> newsDetailsDtoList = new ArrayList<>();
        List<CommentDto> commentDtoList = new ArrayList<>();
        var newsDetailsList = newsService.getNewsById(newsId);
        //var commentList = commentService.getByNewsId(newsId);
        Page<Comment> page = commentService.findPaginated(newsService.getNewsByNewsId(newsId).getId(), pageId, pageSize);
        List<Comment> commentListView = page.getContent();
        newsDetailsList.forEach(news -> {
            var teacherList = userService.findByRoleAndNews("TEACHER", news.getId());
            var studentList = userService.findByRoleAndNews("STUDENT", news.getId());
            newsDetailsDtoList.add(new NewsDto(
                    news.getId(),
                    news.getTitle(),
                    news.getContent(),
                    news.getTimestamp(),
                    teacherList,
                    studentList));
        });
        commentListView.forEach(comment -> {
            var teacherList = userService.findByRoleAndComment("TEACHER", comment.getId());
            var studentList = userService.findByRoleAndComment("STUDENT", comment.getId());
            commentDtoList.add(new CommentDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.getTimestamp(),
                    teacherList,
                    studentList,
                    studentList.size()));
        });
        model.addAttribute("newsId", newsId);
        model.addAttribute("newsDetailsDtoList", newsDetailsDtoList);
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("comment", new Comment());
        model.addAttribute("currentPage", pageId);
        model.addAttribute("totalsPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "news-details";
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @GetMapping("/{newsId}")
    public String addCmt(@PathVariable("newsId") Long newsId, Model model) {
        model.addAttribute("newsId", newsId);
        model.addAttribute("comment", new Comment());
        return "news-details";
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    @PostMapping()
    public String postAddComment(@RequestParam(value = "newsId") Long newsId,@NotNull Comment comment) {
        comment.setTimestamp(new Date());
        comment.setNews(newsService.getNewsByNewsId(newsId));
        comment.setUser(userService.getCurrentUser());
        commentService.addComment(comment);
        return "redirect:/news-details?newsId=" + newsId;
    }
}
