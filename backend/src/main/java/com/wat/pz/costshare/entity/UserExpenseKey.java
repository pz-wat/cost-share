package com.wat.pz.costshare.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserExpenseKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expense_id")
    private Long expenseId;

    public UserExpenseKey(Long userId, Long expenseId) {
        this.userId = userId;
        this.expenseId = expenseId;
    }

}
