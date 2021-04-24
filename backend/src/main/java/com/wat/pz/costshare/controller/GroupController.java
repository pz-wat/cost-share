package com.wat.pz.costshare.controller;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupPostResponseDto;
import com.wat.pz.costshare.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/group")
    public ResponseEntity<GroupPostResponseDto> postGroup(@RequestBody @Valid GroupPostRequestDto groupPostRequestDto) {
        return new ResponseEntity<>(groupService.createGroup(groupPostRequestDto), HttpStatus.CREATED);
    }

}
