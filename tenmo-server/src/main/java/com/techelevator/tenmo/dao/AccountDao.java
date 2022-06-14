package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    List<Account> list();

    void create(Account account);

    Account getAccount(int userId);

    void addMoneyToAccount(BigDecimal amount, int userId);

    void subtractMoneyFromAccount(BigDecimal amount, int userId);

    BigDecimal getBalance(int userId);

}