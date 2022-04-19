package com.virtualclassroom.service.classroom;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.repository.ClassroomRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    @Override
    public Classroom getClassroomById(Long id) {
        return classroomRepository.getById(id);
    }

    @Override
    public void createClass(Classroom classroom) {
        classroom.setCodeClass(getRandomNumberString());
        classroomRepository.save(classroom);
    }

}
