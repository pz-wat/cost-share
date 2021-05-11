package com.wat.pz.costshare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExpensePostRequestDto {

    @NotBlank
    private Long groupId;

    @NotBlank
    private String name;

    @NotBlank
    private BigDecimal amount;

    private List<Long> userIds;

}
