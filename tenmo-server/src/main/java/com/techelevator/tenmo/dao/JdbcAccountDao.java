package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;

//created by Rhye
@Service
public class JdbcAccountDao implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao() {
    }

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int id) {
        String sql = "select balance from account where user_id = ?";
        BigDecimal balance = null;
        try {
            balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, id);
        } catch (DataAccessException e) {
                System.out.println("Error accessing database");
            }
            return balance;
    }

    @Override
    public BigDecimal addToBalance(BigDecimal amountAdded, int id) {
        Account account = findAccountById(id);
        BigDecimal newBalance = account.getBalance().add(amountAdded);
        System.out.println(newBalance);
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, newBalance, id);
        } catch (DataAccessException e) {
            System.err.println("Error accessing data");
        }
        return account.getBalance();
    }

    @Override
    public BigDecimal takeFromBalance(BigDecimal amountTaken, int id) {
        Account account = findAccountById(id);
        BigDecimal newBalance = account.getBalance().subtract(amountTaken);
        System.out.println(newBalance);
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        try {
            jdbcTemplate.update(sql, newBalance, id);
        } catch (DataAccessException e) {
            System.err.println("Error accessing data");
        }
        return account.getBalance();
    }

    @Override
    public Account findUserById(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            account = mapRowToAccount(result);
        } catch (DataAccessException e) {
            System.err.println("Error accessing data");
        }
        return account;
    }

    @Override
    public Account findAccountById(int id) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        Account account = null;
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
            if (result.next()) {
                account = mapRowToAccount(result);
            }
        } catch (DataAccessException e) {
            System.err.println("Error accessing data");
        }
        return account;
    }

    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setBalance(result.getBigDecimal("balance"));
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        return account;
    }
}
