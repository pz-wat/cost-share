package com.wat.pz.costshare.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupJoinRequestDto {
    private Long userId;
    private String accessCode;
}
