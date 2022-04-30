package com.virtualclassroom.repository;

import com.virtualclassroom.model.Comment;
import com.virtualclassroom.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT comment FROM Comment comment WHERE user.id = ?1")
    Comment findByUsername(String username);

    @Query("SELECT comment FROM Comment comment WHERE comment.id = ?1")
    Optional<Comment> findById(Long id);

    @Query("SELECT comment FROM Comment comment JOIN comment.news news WHERE news.id = comment.news.id AND news.id = :newsId")
    List<Comment> findByNewsId(@Param("newsId") Long newsId);
}
