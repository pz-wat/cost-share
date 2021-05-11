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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public ExpenseResponseDto createExpense(Long userId, Long groupId, ExpensePostRequestDto expenseDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User with the provided id does not exist!"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Error: Group with the provided id does not exist!"));

        Set<User> borrowers = new HashSet<>();

        List<ExpenseUser> expenseUsers = new ArrayList<>();
        expenseUsers
                .add(new ExpenseUser(user.getId(), user.getUsername(), new BigDecimal("0.0"), true, true));

        expenseDto.getUserIds().forEach(id -> {
            User tempUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Error: User with the provided id does not exist!"));
            borrowers.add(tempUser);
        });


        if (borrowers.size() > 0) {
            BigDecimal userCount = new BigDecimal(borrowers.size());
            BigDecimal amountPerUser = expenseDto.getAmount().divide(userCount, 2, RoundingMode.HALF_EVEN);
            borrowers.forEach(borrower ->
                    expenseUsers.add(new ExpenseUser(borrower.getId(), borrower.getUsername(), amountPerUser, false, false)));
        }

        Expense expense = new Expense();
        expense.setName(expenseDto.getName());
        expense.setAmount(expenseDto.getAmount());
        expense.addGroup(group);
        expense.addOwner(user);
        expense.addBorrowers(borrowers);

        expenseRepository.save(expense);

        return new ExpenseResponseDto(expense.getId(), expense.getName(), expense.getAmount(),
                expense.getDateCreated(), expense.getGroup().getId(), expenseUsers);
    }

}
