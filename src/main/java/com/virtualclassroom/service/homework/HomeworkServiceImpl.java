package com.virtualclassroom.service.homework;

import com.virtualclassroom.model.Homework;
import com.virtualclassroom.repository.HomeworkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeworkServiceImpl implements HomeworkService{
    private final HomeworkRepository homeworkRepository;

    public HomeworkServiceImpl(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }

    @Override
    public List<Homework> getAllHomework() {
        return homeworkRepository.findAll();
    }

    @Override
    public void createHomework(Homework homework) {
         homeworkRepository.save(homework);
    }

    @Override
    public List<Homework> getHomeworkByClassIdAndUsername(Long id, String username) {
        return homeworkRepository.findByClassIdAndUser(id, username);
    }

    @Override
    public Optional<Homework> findHomeworkById(Long id) {
        return homeworkRepository.findById(id);
    }
}
