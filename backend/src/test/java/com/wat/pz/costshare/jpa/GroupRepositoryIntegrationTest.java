package com.wat.pz.costshare.jpa;

import com.wat.pz.costshare.entity.Group;
import com.wat.pz.costshare.repository.GroupRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class GroupRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupRepository groupRepository;

    @AfterEach
    public void cleanUp() {
        groupRepository.deleteAll();
    }

    @Test
    public void whenFindByAccessCode_thenReturnGroup() {
        Group group = new Group();
        group.setName("Holidays");
        group.setAccessCode("tEsT1234");

        entityManager.persistAndFlush(group);

        Group fromDb = groupRepository.findByAccessCode(group.getAccessCode()).orElse(null);
        assertNotNull(fromDb);
        assertEquals(group.getAccessCode(), fromDb.getAccessCode());
    }

    @Test
    public void whenFindByWrongAccessCode_thenReturnNull() {
        Group fromDb = groupRepository.findByAccessCode("wrong12").orElse(null);

        assertNull(fromDb);
    }

}
