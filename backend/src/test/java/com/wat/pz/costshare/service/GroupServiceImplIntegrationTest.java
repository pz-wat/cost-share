package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.response.GroupResponseDto;
import com.wat.pz.costshare.entity.Group;
import com.wat.pz.costshare.repository.GroupRepository;
import com.wat.pz.costshare.repository.UserRepository;
import com.wat.pz.costshare.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class GroupServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @MockBean
        static UserRepository userRepository;

        @MockBean
        static GroupRepository groupRepository;

        @Bean
        public GroupService groupService() {
            return new GroupServiceImpl(userRepository, groupRepository);
        }
    }

    @Autowired
    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        Group holidays = new Group();
        holidays.setId(1L);
        holidays.setName("Holidays");
        holidays.setAccessCode("test1234");

        Group fuel = new Group();
        fuel.setId(2L);
        fuel.setName("Fuel");
        fuel.setAccessCode("test5678");

        Group pizza = new Group();
        pizza.setId(3L);
        pizza.setName("Pizza");
        pizza.setAccessCode("test9012");

        List<Group> allGroups = Arrays.asList(holidays, fuel, pizza);

        Mockito.when(EmployeeServiceImplTestContextConfiguration.groupRepository.findById(holidays.getId())).thenReturn(Optional.of(holidays));
        Mockito.when(EmployeeServiceImplTestContextConfiguration.groupRepository.findById(-111L)).thenReturn(Optional.empty());
        Mockito.when(EmployeeServiceImplTestContextConfiguration.groupRepository.findByAccessCode(holidays.getAccessCode())).thenReturn(Optional.of(holidays));
        Mockito.when(EmployeeServiceImplTestContextConfiguration.groupRepository.findByAccessCode("wrong123")).thenReturn(Optional.empty());
        Mockito.when(EmployeeServiceImplTestContextConfiguration.groupRepository.findAll()).thenReturn(allGroups);

    }

    @Test
    public void whenValidAccessCode_thenShouldBeFound() {
        String accessCode = "test1234";
        GroupResponseDto groupResponseDto = groupService.findGroupByAccessCode(accessCode);

        assertNotNull(groupResponseDto);
        assertEquals(accessCode, groupResponseDto.getAccessCode());
    }

    @Test
    public void whenNotValidAccessCode_thenShouldNotBeFound() {
        String accessCode = "wrong123";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GroupResponseDto groupResponseDto = groupService.findGroupByAccessCode(accessCode);
        });

        String expectedMessage = "Group with the provided access code does not exist!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
