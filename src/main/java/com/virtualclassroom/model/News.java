package com.virtualclassroom.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToOne
    @JoinColumn(name = "class_id", referencedColumnName = "id", nullable = false)
    private Classroom classrooms;

    @OneToMany()
    @JoinTable(name = "News_user",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    public News() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) && Objects.equals(classrooms, news.classrooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classrooms);
    }
}
