package com.virtualclassroom.service.user;

import com.virtualclassroom.model.Role;
import com.virtualclassroom.model.User;
import com.virtualclassroom.repository.RoleRepository;
import com.virtualclassroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUserWithDefaultRole (User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);

        Role roleUser = roleRepository.findByName("STUDENT");
        user.addRole(roleUser);

        userRepository.save(user);
    }

    public void save(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getCurrentUser() {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(currentUsername);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public Set<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Set<User> findByRoleAndClassroom(String role, Long classroomId) {
        return userRepository.findByRoleAndClassroom(role, classroomId);
    }

    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
