package com.wat.pz.costshare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class GroupExpense {
    private Long id;
    private String name;
    private LocalDate date;
}
