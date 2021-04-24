package com.wat.pz.costshare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class GroupDetailsResponseDto {
    private Long id;
    private String name;
    private String accessCode;
    private Set<ExpenseDto> expenses;
}
