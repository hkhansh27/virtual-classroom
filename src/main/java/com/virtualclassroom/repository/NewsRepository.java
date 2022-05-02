package com.virtualclassroom.repository;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT news FROM News news WHERE news.id = ?1")
    List<News> findByNewsId(@Param("newsId") Long newsId);

    @Query("SELECT news FROM News news WHERE news.id = ?1")
    News getById(Long id);
    @Query("SELECT DISTINCT news FROM News news JOIN news.user user  WHERE user.userName = :userName")
    List<News> findByUserName(@Param("userName") String userName);

    @Query("SELECT news FROM News news JOIN news.classroom classroom WHERE classroom.id = news.classroom.id AND classroom.id = :classroomId")
    List<News> findByClassId(@Param("classroomId") Long classroomId);
    @Query("SELECT DISTINCT news FROM News news JOIN news.classroom classroom  WHERE classroom.id = :classroomId")
    Page<News> findByClassroomIdPagination(@Param("classroomId") Long classroomId, Pageable pageable);
}
