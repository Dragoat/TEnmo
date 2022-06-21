package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class AccountController {

    AccountDao accountDao;
    UserDao userDao;

    @Autowired
    public AccountController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    //endpoint that gives the balance of the authenticated user.
    @RequestMapping(path = "account/balance", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(Principal principal) {

        //gets username of current login user.
        String username = principal.getName();
        //gets the userId
        int userId = userDao.findIdByUsername(username);
        //gets the account info
        Account account = accountDao.getAccount(userId);
        //returns the balance of the account
        return account.getBalance();

    }

    //endpoint that give the list of the users available to send money
    @RequestMapping(path = "account/users", method = RequestMethod.GET)
    public List<User> getUsersToSendMoney(Principal principal) {
        //gets the username list based on comparing the current username pattern
        int id = userDao.findIdByUsername(principal.getName());
        //returns user you can send money not including yourself
        return userDao.findAllForSendingMoney(id);
    }
}