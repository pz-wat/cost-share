package com.wat.pz.costshare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupPostRequestDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private String name;

}
