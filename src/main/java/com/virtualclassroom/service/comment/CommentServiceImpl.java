package com.virtualclassroom.service.comment;

import com.virtualclassroom.model.Comment;
import com.virtualclassroom.model.News;
import com.virtualclassroom.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Comment> getByNewsId(Long id) {
        return commentRepository.findByNewsId(id);
    }
    @Override
    public Page<Comment> findPaginated(Long newsId, int pageId, int pageSize) {
        Pageable pageable = PageRequest.of(pageId - 1, pageSize);
        return this.commentRepository.findByNewsIdPagination(newsId, pageable);
    }
}
