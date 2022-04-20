package com.virtualclassroom.repository;

import com.virtualclassroom.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT news FROM News news WHERE news.id = ?1")
    Optional<News> findById(Long id);
}
