package com.wat.pz.costshare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "expense")
public class Expense {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expense")
    private Set<UserExpense> userExpenses = new HashSet<>();

    public void addGroup(Group group) {
        this.setGroup(group);
        group.getExpenses().add(this);
    }

    public void addOwner(User user) {
        addUser(user, new BigDecimal("0.0"),true, true);
    }

    public void addBorrowers(Set<User> borrowers, BigDecimal owedAmount) {
        borrowers.forEach(borrower -> addUser(borrower, owedAmount, false, false));
    }

    private void addUser(User user, BigDecimal owedAmount, boolean paid, boolean settled) {
        UserExpense userExpense = new UserExpense(user, this, owedAmount, paid, settled);
        userExpenses.add(userExpense);
        user.getUserExpenses().add(userExpense);
    }
}
