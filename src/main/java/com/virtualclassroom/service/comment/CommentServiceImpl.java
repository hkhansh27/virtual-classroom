package com.virtualclassroom.service.comment;

import com.virtualclassroom.model.Comment;
import com.virtualclassroom.model.News;
import com.virtualclassroom.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    public final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }
}