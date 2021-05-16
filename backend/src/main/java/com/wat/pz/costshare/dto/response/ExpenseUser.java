package com.wat.pz.costshare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseUser {
    private Long id;
    private String username;
    private BigDecimal owedAmount;
    private boolean paid;
    private boolean settled;
}
