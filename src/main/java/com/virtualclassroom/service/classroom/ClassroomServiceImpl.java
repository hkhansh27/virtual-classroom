package com.virtualclassroom.service.classroom;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassRepository classRepository;

    public ClassroomServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
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
    public void createClass(Classroom classroom) {
        classroom.setCodeClass(getRandomNumberString());
        classRepository.save(classroom);
    }
}
