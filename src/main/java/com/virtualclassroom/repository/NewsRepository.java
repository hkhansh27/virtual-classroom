package com.virtualclassroom.repository;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT news FROM News news WHERE news.id = ?1")
    Optional<News> findById(Long id);

    @Query("SELECT DISTINCT news FROM News news JOIN news.user user  WHERE user.userName = :userName")
    List<News> findByUserName(@Param("userName") String userName);

    @Query(value="SELECT DISTINCT news FROM News news JOIN news.classrooms classroom  WHERE classroom.id = :classroomId", nativeQuery = true)
    List<News> findByClassId(@Param("classroomId") Long classroomId);
}
