package com.wat.pz.costshare.repository;

import com.wat.pz.costshare.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
