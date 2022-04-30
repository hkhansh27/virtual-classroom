package com.virtualclassroom.service.user;

import com.virtualclassroom.model.Role;
import com.virtualclassroom.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getCurrentUser();
    List<User> getAllUsers();
    void addUser(User user);
    void saveUserWithDefaultRole(User user);
    User findByUsername(String username);
    User findByEmail(String username);
    Set<User> findByRole(String role);
    Set<User> findByRoleAndClassroom(String role, Long classroomId);
    Set<User> findByRoleAndNews(String role, Long newsId);
    Set<User> findByRoleAndComment(String role, Long commentId);
    User get(Long id);
    List<Role> getRoles();
    void save(User user);
}

