package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
//created by Rhye
public interface AccountDao {
    BigDecimal getBalance(int userId);
    BigDecimal addToBalance(BigDecimal amountAdded, int id);
    BigDecimal takeFromBalance(BigDecimal amountTaken, int id);
    Account findUserById(int userId);
    Account findAccountById(int id);
}
