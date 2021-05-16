package com.wat.pz.costshare.service;

import com.wat.pz.costshare.dto.request.ExpensePostRequestDto;
import com.wat.pz.costshare.dto.response.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {
    ExpenseResponseDto createExpense(Long userId, Long groupId, ExpensePostRequestDto expenseDto);

    ExpenseResponseDto findExpenseById(Long expenseId);

    List<ExpenseResponseDto> findAllExpensesByGroupId(Long groupId);

    List<ExpenseResponseDto> findAllExpensesByGroupAndUserId(Long groupId, Long userId);
}
