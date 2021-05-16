package com.wat.pz.costshare.controller;

import com.wat.pz.costshare.dto.request.ExpensePostRequestDto;
import com.wat.pz.costshare.dto.response.ExpenseResponseDto;
import com.wat.pz.costshare.dto.response.MessageResponse;
import com.wat.pz.costshare.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable Long expenseId) {
        return new ResponseEntity<>(expenseService.findExpenseById(expenseId), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}/expense")
    public ResponseEntity<List<ExpenseResponseDto>> getGroupExpenses(@PathVariable Long groupId) {
        return new ResponseEntity<>(expenseService.findAllExpensesByGroupId(groupId), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}/user/{userId}/expense")
    public ResponseEntity<List<ExpenseResponseDto>> getGroupUserExpenses(@PathVariable Long groupId,
                                                                         @PathVariable Long userId) {
        return new ResponseEntity<>(expenseService.findAllExpensesByGroupAndUserId(groupId, userId), HttpStatus.OK);
    }

    @PostMapping("/group/{groupId}/user/{userId}/expense")
    public ResponseEntity<ExpenseResponseDto> postExpense(@PathVariable Long groupId, @PathVariable Long userId,
                                                          @RequestBody @Valid ExpensePostRequestDto expenseDto) {
        return new ResponseEntity<>(expenseService.createExpense(groupId, userId, expenseDto), HttpStatus.CREATED);
    }

    @PutMapping("/expense/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable Long expenseId) {
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/expense/{expenseId}/user/{userId}/settle")
    public ResponseEntity<MessageResponse> settleUser(@PathVariable Long expenseId, @PathVariable Long userId) {
        expenseService.settleUser(expenseId, userId);
        return new ResponseEntity<>(new MessageResponse("User with provided id settled successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/expense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long expenseId) {
        return ResponseEntity.badRequest().build();
    }

}
