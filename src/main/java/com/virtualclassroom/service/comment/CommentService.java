package com.virtualclassroom.service.comment;

import com.virtualclassroom.model.Comment;
import com.virtualclassroom.model.News;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getByNewsId(Long id);
}
