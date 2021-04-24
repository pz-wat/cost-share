package com.wat.pz.costshare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GroupResponseDto {
    private Long id;
    private String name;
    private String accessCode;
    private LocalDate date;
    private boolean isUserAdmin;
    private List<GroupExpense> groupExpenses;
    private List<GroupUser> groupUsers;
}
