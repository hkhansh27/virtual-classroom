package com.virtualclassroom.repository;

import com.virtualclassroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user WHERE user.userEmail = ?1")
    User findByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.userName = ?1")
    User findByUsername(String username);

    @Query("SELECT DISTINCT user FROM User user JOIN user.roles role  WHERE role.name = :role")
    Set<User> findByRole(@Param("role") String role);

    @Query("SELECT DISTINCT user FROM User user JOIN user.roles role JOIN user.classrooms classroom WHERE role.name = :role AND classroom.id = :classroomId")
    Set<User> findByRoleAndClassroom(@Param("role") String role, @Param("classroomId") Long classroomId);

    @Query("SELECT DISTINCT user FROM User user JOIN user.roles role JOIN user.news news WHERE role.name = :role AND news.id = :newsId")
    Set<User> findByRoleAndNews(@Param("role") String role, @Param("newsId") Long newsId);

    @Query("SELECT DISTINCT user FROM User user JOIN user.roles role JOIN user.comments comment WHERE role.name = :role AND comment.id = :commentId")
    Set<User> findByRoleAndComment(@Param("role") String role, @Param("commentId") Long commentId);

    public User findByResetPassToken(String token);
}