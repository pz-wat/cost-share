package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.request.GroupPostRequestDto;
import com.wat.pz.costshare.dto.response.GroupPostResponseDto;

public interface GroupService {
    GroupPostResponseDto createGroup(GroupPostRequestDto groupPostRequestDto);
}
