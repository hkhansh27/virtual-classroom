package com.virtualclassroom.repository;

import com.virtualclassroom.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    @Query("SELECT DISTINCT homework FROM Homework homework JOIN homework.classroom classroom JOIN homework.users user WHERE user.userName = :username AND classroom.id = :classroomId")
    List<Homework> findByClassIdAndUser(@Param("classroomId") Long classroomId, @Param("username") String username);

    @Query("SELECT DISTINCT homework FROM Homework homework JOIN homework.classroom classroom JOIN homework.users user JOIN user.roles role WHERE role.name = 'TEACHER' AND classroom.id = :classroomId")
    List<Homework> findHomeworkByTeacher(@Param("classroomId") Long classroomId);

    @Query("SELECT DISTINCT homework FROM Homework homework JOIN homework.classroom classroom WHERE classroom.id = :classroomId")
    List<Homework> findHomeworkByClassroomId(@Param("classroomId") Long classroomId);
}
