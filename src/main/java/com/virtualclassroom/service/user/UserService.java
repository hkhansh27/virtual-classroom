package com.virtualclassroom.service.user;

import com.virtualclassroom.model.Role;
import com.virtualclassroom.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(User user);
    void saveUserWithDefaultRole(User user);
    User findByUsername(String username);
    User findByEmail(String username);
    User get(Long id);
    List<Role> getRoles();
    void save(User user);
}

