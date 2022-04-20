package com.virtualclassroom.service.classroom;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.repository.ClassroomRepository;
import com.virtualclassroom.utils.Helper;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }
    @Override
    public Classroom getClassroomById(Long id) {
        return classroomRepository.getById(id);
    }

    @Override
    public void createClass(Classroom classroom) {
        classroom.setCodeClass(Helper.getRandomNumberString());
        classroomRepository.save(classroom);
    }

}
