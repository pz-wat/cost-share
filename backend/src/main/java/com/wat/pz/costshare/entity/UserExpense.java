package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_expense")
public class UserExpense {

    @EmbeddedId
    private UserExpenseKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("expenseId")
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @Column(name = "owed_amount")
    private BigDecimal owedAmount;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "settled")
    private boolean settled;

    public UserExpense(User user, Expense expense, BigDecimal owedAmount, boolean paid, boolean settled) {
        this.id = new UserExpenseKey(user.getId(), expense.getId());
        this.user = user;
        this.expense = expense;
        this.owedAmount = owedAmount;
        this.paid = paid;
        this.settled = settled;
    }
}
