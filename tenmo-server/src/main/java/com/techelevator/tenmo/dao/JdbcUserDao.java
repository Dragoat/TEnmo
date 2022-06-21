package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private final JdbcTemplate jdbcTemplate;
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int findIdByUsername(String username) {
        //data base query to get username
        String sql = "SELECT user_id FROM tenmo_user WHERE username ILIKE ?;";
        //convert the row into an int
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        //checks if the id is null or valid
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        //data base query to get userId, username and password
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user;";
        //convert the row into an object via row mapper.
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        //checks if results have more than one object
        while(results.next()) {
            //gets object attributes from database
            User user = mapRowToUser(results);
            //adds object to a list
            users.add(user);
            System.out.println("Added" + user.getUsername());
        }
        return users;
    }
    @Override
    public List<User> findAllForSendingMoney(int id) {
        System.out.println(id);
        List<User> users = new ArrayList<>();
        //data base query to get userId thats not the curren userId's
        String sql = "SELECT user_id, username FROM tenmo_user WHERE user_id != ?;";
        //convert the row into an object via row mapper.
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        //checks if results have more than one object
        while(results.next()) {
            //gets object attributes from database
            User user = mapRowToPartUser(results);
            //adds object to a list
            users.add(user);
        }
        return users;
    }

    @Override
    public String findUserNameByAccountId(int id) {
        //data base query that joins tenmouser and account to get username
        String sql = "SELECT username FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id" +
                " WHERE account_id = ?";
        //convert the row into an object via row mapper.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        String userName = "";

        //check if rowset next attribute is not null
        if(rowSet.next()) {
            //gets attribute named "username"
            userName = rowSet.getString("username");
        }

        //returns the username
        return userName;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        //data base query
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user WHERE username ILIKE ?;";
        //convert the row into an object via row mapper.
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()){
            return mapRowToUser(rowSet);
        }
        //if rowset is null there is no username in the database
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {
        // create new user
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
        } catch (DataAccessException e) {
            return false;
        }
        // create account
        sql = "INSERT INTO account (user_id, balance) values(?, ?)";
        try {
            jdbcTemplate.update(sql, newUserId, STARTING_BALANCE);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
    private User mapRowToPartUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}
