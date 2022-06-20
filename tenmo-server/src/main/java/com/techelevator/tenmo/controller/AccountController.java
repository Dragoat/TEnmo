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
public class AccountController {  //gets the request from client on port 8080

    AccountDao accountDao; //controller talks through the dao
    UserDao userDao;

    @Autowired
    public AccountController(UserDao userDao, AccountDao accountDao) {  //when we create an account controller, i need a user dao
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

        //
        Account account = accountDao.getAccount(userId);
        return account.getBalance();

    }

    @RequestMapping(path = "account/users", method = RequestMethod.GET)
    public List<User> getUsersToSendMoney(Principal principal) {
        int id = userDao.findIdByUsername(principal.getName());
        return userDao.findAllForSendingMoney(id);
    }
}