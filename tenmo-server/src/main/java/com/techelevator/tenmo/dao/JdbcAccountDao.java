package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {
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
        //data base query to get user info
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        //convert the row into an object
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        //checks if object is not null
        if (result.next()) {
            account = mapRowToAccount(result);
        }
        //returns the account object found by using the userId
        return account;
    }

    @Override
    public void subtractMoneyFromAccount(BigDecimal amount, int userId){
        //updates info from substracted account or account that send money
        String sql= "UPDATE account SET balance = balance + ? WHERE user_id = ?";
        //stores new balance
        BigDecimal newBalance = getBalance(userId).add(amount);
        //updates the new balance with user with the same userId
        jdbcTemplate.update(sql, newBalance, userId);
    }

    @Override
    public BigDecimal getBalance(int userId) {
        Account account = new Account();
        //data base query to get info from the account table using the  userId
        String sql = "SELECT * FROM account WHERE user_id = ?";
        //convert the row into an object
        SqlRowSet balance = jdbcTemplate.queryForRowSet(sql, userId);
        //check if balance is not null
        if (balance.next()){
            account = mapRowToAccount(balance);
        }
        //returns balance
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