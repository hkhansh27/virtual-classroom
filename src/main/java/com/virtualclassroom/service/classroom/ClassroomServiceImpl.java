package com.virtualclassroom.service.classroom;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.repository.ClassRepository;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassRepository classRepository;

    public ClassroomServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public void createClass(Classroom classroom) {
        classRepository.save(classroom);
    }
}
