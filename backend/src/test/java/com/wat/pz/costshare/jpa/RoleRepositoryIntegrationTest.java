package com.wat.pz.costshare.jpa;

import com.wat.pz.costshare.entity.Role;
import com.wat.pz.costshare.repository.RoleRepository;
import com.wat.pz.costshare.type.ERole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    public void cleanUp() {
        roleRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnRole() {
        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        entityManager.persistAndFlush(role);

        Role fromDb = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        assertNotNull(fromDb);
        assertEquals(role.getName(), fromDb.getName());
    }
}
