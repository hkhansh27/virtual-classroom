package com.virtualclassroom.service.classroom;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.repository.ClassroomRepository;
import com.virtualclassroom.utils.Helper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<Classroom> getAllClasses() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom getClassroomById(Long id) {
        return classroomRepository.getById(id);
    }

    @Override
    public List<Classroom> getClassesByUsername(String username) {
        return classroomRepository.findByUserName(username);
    }

    @Override
    public void createClass(Classroom classroom) {
        classroom.setCodeClass(Helper.getRandomNumberString());
        classroomRepository.save(classroom);
    }

    public Classroom findClassByCodeID(String keyword) {
        if (keyword != null) {
            return classroomRepository.findbyKey(keyword);
        }
        return null;
    }
}
