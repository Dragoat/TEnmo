package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {//all database to server
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        account.setBalance(result.getBigDecimal("balance"));
        return account;
    }

    @Override
    public Account getAccount(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if (result.next()){
            account = mapRowToAccount(result);
        }
        return account;
    }

    @Override
    public void subtractMoneyFromAccount(BigDecimal amount, int userId){
        String sql= "UPDATE account SET balance = balance + ? WHERE user_id = ?";
        BigDecimal newBalance = getBalance(userId).add(amount);
        jdbcTemplate.update(sql, newBalance, userId);
    }

    @Override
    public BigDecimal getBalance(int userId) {
        Account account = new Account();                                      // contains all data for account
        String sql = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet balance = jdbcTemplate.queryForRowSet(sql, userId);
        if (balance.next()){
            account = mapRowToAccount(balance);                               // we implement helper method to set or object to balance
        }
        return account.getBalance();
    }
    @Override
    public void addMoneyToAccount(BigDecimal amount, int userId) {}

    @Override
    public List<Account> list() {
        return null;
    }

    @Override
    public void create(Account account) {}
}