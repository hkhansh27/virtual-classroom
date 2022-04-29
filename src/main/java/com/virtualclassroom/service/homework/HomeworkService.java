package com.virtualclassroom.service.homework;

import com.virtualclassroom.model.Homework;

import java.util.List;
import java.util.Optional;

public interface HomeworkService {
    List<Homework> getAllHomework();
    void saveHomework(Homework homework);
    void createHomework(Homework homework);
    Optional<Homework> findHomeworkById(Long id);
    List<Homework> getHomeworkByClassIdAndUsername(Long id, String username);
    List<Homework> findHomeworkByTeacher(Long classroomId);
    List<Homework> findHomeworkByClassroomId(Long classroomId);
}
