package com.wat.pz.costshare.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseDto {
    private Long id;
    private String name;
    private LocalDate date;
}
