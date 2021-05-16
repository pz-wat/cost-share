package com.wat.pz.costshare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseResponseDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private LocalDate dateCreated;
    private Long groupId;
    private List<ExpenseUser> users;
}
