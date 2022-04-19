package com.virtualclassroom;

import com.virtualclassroom.model.Classroom;
import com.virtualclassroom.repository.ClassRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Random;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureDataJpa
@Rollback(false)
public class ClassRepositoryTest {
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
    @Autowired
    private ClassRepository repo;

    @Test
    public void testCreateClass() {
        Classroom classRoom = new Classroom();
        classRoom.setCodeClass(getRandomNumberString());
        classRoom.setDescriptionClass("Java Together");
        classRoom.setNameClass("Java Class");

        repo.save(classRoom);
        assertThat(classRoom.getId()).isGreaterThan(0);
    }
}
