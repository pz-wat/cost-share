package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupDto;
import com.wat.pz.costshare.dto.response.GroupPostResponseDto;

import java.util.List;

public interface GroupService {
    GroupPostResponseDto createGroup(GroupPostRequestDto groupPostRequestDto);

    List<GroupDto> findAllByUserId(Long userId);
}
