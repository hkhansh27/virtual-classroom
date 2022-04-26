package com.virtualclassroom.repository;

import com.virtualclassroom.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    @Query("SELECT DISTINCT homework FROM Homework homework JOIN homework.classrooms classroom JOIN homework.users user WHERE user.userName = :username AND classroom.id = :classroomId")
    List<Homework> findByClassIdAndUser(@Param("classroomId") Long classroomId, @Param("username") String username);
}
