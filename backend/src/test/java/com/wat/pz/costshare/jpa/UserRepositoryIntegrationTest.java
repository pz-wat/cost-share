package com.wat.pz.costshare.jpa;

import com.wat.pz.costshare.entity.User;
import com.wat.pz.costshare.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        User user = new User();
        user.setUsername("test123");
        user.setPassword("test12345");
        user.setEmail("test@example.com");

        entityManager.persistAndFlush(user);
        User fromDb = userRepository.findByUsername(user.getUsername()).orElse(null);

        assertNotNull(fromDb);
        assertEquals(fromDb.getUsername(), user.getUsername());
        assertEquals(fromDb.getPassword(), user.getPassword());
        assertEquals(fromDb.getEmail(), user.getEmail());
    }

    @Test
    public void whenFindByWrongUsername_thenReturnNull() {
        User fromDb = userRepository.findByUsername("wrongusername").orElse(null);

        assertNull(fromDb);
    }

    @Test
    public void whenFindById_thenReturnUser() {
        User user = new User();
        user.setUsername("test123");
        user.setPassword("test12345");
        user.setEmail("test@example.com");

        entityManager.persistAndFlush(user);
        User fromDb = userRepository.findById(user.getId()).orElse(null);

        assertNotNull(fromDb);
        assertEquals(fromDb.getUsername(), user.getUsername());
        assertEquals(fromDb.getPassword(), user.getPassword());
        assertEquals(fromDb.getEmail(), user.getEmail());
    }

    @Test
    public void whenFindByWrongId_thenReturnNull() {
        User fromDb = userRepository.findById(-111L).orElse(null);

        assertNull(fromDb);
    }

    @Test
    public void whenExistsByUsername_returnTrue() {
        User user = new User();
        user.setUsername("test123");
        user.setPassword("test12345");
        user.setEmail("test@example.com");

        entityManager.persistAndFlush(user);
        boolean exists = userRepository.existsByUsername(user.getUsername());

        assertTrue(exists);
    }

    @Test
    public void whenExistsByWrongUsername_returnFalse() {
        boolean exists = userRepository.existsByUsername("wrongusername");

        assertFalse(exists);
    }

    @Test
    public void whenExistsByEmail_returnTrue() {
        User user = new User();
        user.setUsername("test123");
        user.setPassword("test12345");
        user.setEmail("test@example.com");

        entityManager.persistAndFlush(user);
        boolean exists = userRepository.existsByEmail(user.getEmail());

        assertTrue(exists);
    }

    @Test
    public void whenExistsByWrongEmail_returnFalse() {
        boolean exists = userRepository.existsByEmail("wrongemail");

        assertFalse(exists);
    }
}
