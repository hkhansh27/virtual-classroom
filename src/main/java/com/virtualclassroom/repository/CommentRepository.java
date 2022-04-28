package com.virtualclassroom.repository;

import com.virtualclassroom.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT comment FROM Comment comment WHERE user.id = ?1")
    Comment findByUsername(String username);

    @Query("SELECT comment FROM Comment comment WHERE comment.id = ?1")
    Optional<Comment> findById(Long id);
}
