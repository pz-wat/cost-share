package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.request.ExpensePostRequestDto;
import com.wat.pz.costshare.dto.response.ExpenseResponseDto;

public interface ExpenseService {
    ExpenseResponseDto createExpense(Long userId, Long groupId, ExpensePostRequestDto expenseDto);
}
