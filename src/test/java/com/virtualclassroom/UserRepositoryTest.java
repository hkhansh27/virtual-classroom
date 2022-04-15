package com.virtualclassroom;

import com.virtualclassroom.model.Role;
import com.virtualclassroom.model.User;
import com.virtualclassroom.repository.RoleRepository;
import com.virtualclassroom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureDataJpa
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAddRoleToUser() {
        User user = new User();
        user.setUserName("test");
        user.setUserPassword("test");
        user.setUserEmail("test@gmail.com");
        user.addRole(roleRepository.findByName("TEACHER"));

        userRepository.save(user);
        assert user.getRoles().size() == 1;
    }

    @Test
    public void testAddRolesToExistingUser() {
        User user = userRepository.findById(1L).get();

        Role roleUser = roleRepository.findByName("TEACHER");
        user.addRole(roleUser);

        Role roleAdmin = new Role(2L);
        user.addRole(roleAdmin);

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getRoles().size()).isEqualTo(2);

    }
}
