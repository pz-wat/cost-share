package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_expense")
public class UserExpense {

    @EmbeddedId
    private UserExpenseKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("expenseId")
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @Column(name = "settled")
    private boolean settled;

}
