package com.virtualclassroom.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String userEmail;

    private String userPassword;

    private String userStatus;

    private String resetPassToken;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "user_class",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"))
    private Set<Classroom> classrooms = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Homework> homeworks = new HashSet<>();

    @ManyToMany (mappedBy = "user")
    private Set<News> news = new HashSet<>();

    public User() {
    }

    public User(String userName, String userEmail, String userPassword, String userStatus, String resetPassToken) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.resetPassToken = resetPassToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getResetPassToken() {
        return resetPassToken;
    }

    public void setResetPassToken(String resetPassToken) {
        this.resetPassToken = resetPassToken;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Set<Classroom> getClasses() {
        return classrooms;
    }

    public void setClasses(Set<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addClass(Classroom cls) {
        this.classrooms.add(cls);
    }

    public void removeClass(Classroom cls) {
        this.classrooms.remove(cls);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public Set<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Set<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public Set<Homework> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(Set<Homework> homework) {
        this.homeworks = homework;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
