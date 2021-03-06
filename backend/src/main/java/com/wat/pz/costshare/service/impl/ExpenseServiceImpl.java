package com.wat.pz.costshare.service.impl;

import com.wat.pz.costshare.dto.request.ExpensePostRequestDto;
import com.wat.pz.costshare.dto.response.ExpenseResponseDto;
import com.wat.pz.costshare.dto.response.ExpenseUser;
import com.wat.pz.costshare.entity.Expense;
import com.wat.pz.costshare.entity.Group;
import com.wat.pz.costshare.entity.User;
import com.wat.pz.costshare.repository.ExpenseRepository;
import com.wat.pz.costshare.repository.GroupRepository;
import com.wat.pz.costshare.repository.UserRepository;
import com.wat.pz.costshare.service.ExpenseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public ExpenseResponseDto findExpenseById(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense with the provided id does not exist!"));

        List<ExpenseUser> expenseUsers = new ArrayList<>();
        expense.getUserExpenses()
                .forEach(userExpense -> expenseUsers
                        .add(new ExpenseUser(userExpense.getUser().getId(), userExpense.getUser().getUsername(),
                                userExpense.getOwedAmount(), userExpense.isPaid(), userExpense.isSettled())));

        return  new ExpenseResponseDto(expense.getId(), expense.getName(), expense.getAmount(),
                expense.getDateCreated(), expense.getGroup().getId(), expenseUsers);
    }

    @Override
    @Transactional
    public List<ExpenseResponseDto> findAllExpensesByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));

        List<ExpenseResponseDto> expenses = new ArrayList<>();
        group.getExpenses().forEach(expense -> {
            List<ExpenseUser> expenseUsers = new ArrayList<>();
            expense.getUserExpenses().forEach(userExpense -> expenseUsers.add(new ExpenseUser(userExpense.getUser().getId(),
                    userExpense.getUser().getUsername(), userExpense.getOwedAmount(), userExpense.isPaid(), userExpense.isSettled())));
            expenses.add(new ExpenseResponseDto(expense.getId(), expense.getName(), expense.getAmount(),
                    expense.getDateCreated(), expense.getGroup().getId(), expenseUsers));
        });

        expenses.sort(Comparator.comparing(ExpenseResponseDto::getName));
        return expenses;
    }

    @Override
    @Transactional
    public List<ExpenseResponseDto> findAllExpensesByGroupAndUserId(Long groupId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));

        List<ExpenseResponseDto> expenses = new ArrayList<>();

        user.getUserExpenses().stream()
                .filter(userExpense -> userExpense.getExpense().getGroup().getId().equals(group.getId())
                        && userExpense.getUser().getId().equals(user.getId()))
                .forEach(userExpense -> {
                    Expense tempExpense = userExpense.getExpense();
                    List<ExpenseUser> expenseUsers = new ArrayList<>();
                    tempExpense.getUserExpenses().stream()
                            .filter(ue -> ue.getExpense().getId().equals(tempExpense.getId()))
                            .forEach(ue -> expenseUsers.add(new ExpenseUser(ue.getUser().getId(),
                                    ue.getUser().getUsername(), ue.getOwedAmount(),
                                    ue.isPaid(), ue.isSettled())));
                    ExpenseResponseDto expenseResponse = 
                            new ExpenseResponseDto(tempExpense.getId(), 
                            tempExpense.getName(), tempExpense.getAmount(),
                            tempExpense.getDateCreated(), tempExpense.getGroup().getId(), expenseUsers);
                    expenses.add(expenseResponse);
                });

        expenses.sort(Comparator.comparing(ExpenseResponseDto::getName));
        return expenses;
    }

    @Override
    @Transactional
    public void settleUser(Long expenseId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense with the provided id does not exist!"));

        expense.getUserExpenses().stream()
                .filter(userExpense -> userExpense.getUser().getId().equals(user.getId()))
                .forEach(userExpense -> userExpense.setSettled(true));
        expenseRepository.save(expense);
    }

    @Override
    @Transactional
    public ExpenseResponseDto createExpense(Long groupId, Long userId, ExpensePostRequestDto expenseDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group with the provided id does not exist!"));

        List<ExpenseUser> expenseUsers = new ArrayList<>();
        expenseUsers
                .add(new ExpenseUser(user.getId(), user.getUsername(), new BigDecimal("0.0"), true, true));

        Set<User> borrowers = new HashSet<>();
        expenseDto.getUserIds().forEach(id -> {
            User tempUser = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User with the provided id does not exist!"));
            borrowers.add(tempUser);
        });

        BigDecimal userCount = new BigDecimal((borrowers.size() + 1));
        BigDecimal amountPerUser;
        if (borrowers.size() > 0) {
            amountPerUser = expenseDto.getAmount().divide(userCount, 2, RoundingMode.HALF_UP);
        } else  {
            amountPerUser = new BigDecimal("0.0");
        }
        borrowers.forEach(borrower ->
                expenseUsers.add(new ExpenseUser(borrower.getId(), borrower.getUsername(), amountPerUser, false, false)));

        Expense expense = new Expense();
        expense.setName(expenseDto.getName());
        expense.setAmount(expenseDto.getAmount());
        expense.addGroup(group);
        expense.addOwner(user);
        expense.addBorrowers(borrowers, amountPerUser);
        expenseRepository.save(expense);

        return new ExpenseResponseDto(expense.getId(), expense.getName(), expense.getAmount(),
                expense.getDateCreated(), expense.getGroup().getId(), expenseUsers);
    }

    @Override
    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense with the provided id does not exist!"));
        expenseRepository.delete(expense);
    }

}
