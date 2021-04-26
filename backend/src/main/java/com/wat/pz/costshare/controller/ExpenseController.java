package com.wat.pz.costshare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @GetMapping("/expense/{expenseId}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long expenseId) {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/group/{groupId}/expense")
    public ResponseEntity<?> getGroupExpenses(@PathVariable Long groupId) {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/group/{groupId}/user/{userId}/expense")
    public ResponseEntity<?> getGroupUserExpenses(@PathVariable Long groupId, @PathVariable Long userId) {
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/group/{groupId}/user/{userId}/expense")
    public ResponseEntity<?> postExpense(@PathVariable Long groupId, @PathVariable Long userId) {
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/expense/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable Long expenseId) {
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/expense/{expenseId}/user/{userId}/settle")
    public ResponseEntity<?> settleUser(@PathVariable Long expenseId, @PathVariable Long userId) {
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/expense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long expenseId) {
        return ResponseEntity.badRequest().build();
    }

}
