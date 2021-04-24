package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupResponseDto;

import java.util.List;

public interface GroupService {
    GroupResponseDto createGroup(GroupPostRequestDto groupPostRequestDto);

    GroupResponseDto findGroupByAccessCode(String accessCode);

    List<GroupResponseDto> findAllGroupsByUserId(Long userId);

    void addUserToGroup(Long userId, Long groupId);
}
