package com.virtualclassroom.service.comment;

import com.virtualclassroom.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getByNewsId(Long id);
    Page<Comment> findPaginated(Long newsId, int pageId, int pageSize);
}
