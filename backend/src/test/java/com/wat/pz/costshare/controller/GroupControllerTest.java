package com.wat.pz.costshare.controller;

import com.wat.pz.costshare.CostShareApplication;
import com.wat.pz.costshare.repository.UserRepository;
import com.wat.pz.costshare.security.services.UserDetailsServiceImpl;
import com.wat.pz.costshare.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = GroupController.class,
//        excludeAutoConfiguration = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
//@ContextConfiguration(classes = { UserDetailsServiceImpl.class , UserRepository.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { CostShareApplication.class })
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GroupService groupService;

//    @Test
//    void getGroupByAccessCode() throws Exception {
//
//        mvc.perform(get("/group/{accessCode}", "h7qmH1VQ")
//        ).andExpect(status().isOk());
//    }

    @Test
    void getUserGroups() {
    }

    @Test
    void getGroupById() {
    }

    @Test
    void postGroup() {
    }

    @Test
    void addUserToGroup() {
    }

    @Test
    void deleteGroup() {
    }

    @Test
    void testDeleteGroup() {
    }
}