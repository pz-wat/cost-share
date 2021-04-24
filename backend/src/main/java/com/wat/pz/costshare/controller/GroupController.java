package com.wat.pz.costshare.controller;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupResponseDto;
import com.wat.pz.costshare.dto.response.MessageResponse;
import com.wat.pz.costshare.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group/{accessCode}")
    public ResponseEntity<GroupResponseDto> getGroupByAccessCode(@PathVariable String accessCode) {
        return new ResponseEntity<>(groupService.findGroupByAccessCode(accessCode), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/group")
    public ResponseEntity<List<GroupResponseDto>> getUserGroups(@PathVariable Long userId) {
        return new ResponseEntity<>(groupService.findAllGroupsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long userId, @PathVariable Long groupId) {
        return new ResponseEntity<>(groupService.findGroupById(userId, groupId), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/group")
    public ResponseEntity<GroupResponseDto> postGroup(@RequestBody @Valid GroupPostRequestDto groupPostRequestDto) {
        return new ResponseEntity<>(groupService.createGroup(groupPostRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<MessageResponse> addUserToGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        groupService.addUserToGroup(userId, groupId);
        return new ResponseEntity<>(new MessageResponse("User added to group successfully!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<MessageResponse> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>(new MessageResponse("Group deleted successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<MessageResponse> deleteGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        groupService.deleteUserFromGroup(userId, groupId);
        return new ResponseEntity<>(new MessageResponse("Group deleted successfully!"), HttpStatus.OK);
    }

}
