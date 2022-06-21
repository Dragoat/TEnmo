package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/transfers")
public class TransferController {
    TransferDao transferDao;
    UserDao userDao;
    AccountDao accountDao;

    @Autowired
    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    //gets the info of the user they are going to deposit to.
    public void depositMoney(BigDecimal amount, int id){
        accountDao.getAccount(id);
    }

    //get list of transaction history
    @RequestMapping(method = RequestMethod.GET)
    public List<Transfer> getListOfTransfers(Principal principal) throws Exception {
        //gets the current user id
        int userId = userDao.findIdByUsername(principal.getName());
        //returns the list of transfers made by the user with that userId
        return transferDao.getTransfersList(userId);
    }



    @RequestMapping(method = RequestMethod.PUT)
    public void withdrawFromSender(@RequestBody Transfer transfer, Principal principal) throws Exception{
        //gets the sendersId
        int senderId =  userDao.findIdByUsername(principal.getName());
        //sets the sender id
        transfer.setSenderId(userDao.findIdByUsername(principal.getName()));
        //substract balance from sender
        transferDao.subtractFromSenderBalance(senderId, transfer.getAmount() );
        //adds the balance to reciever
        transferDao.addToReceiverBalance(transfer.getReceiverId(), transfer.getAmount());
        //creates transfer record for sender
        transferDao.createTransfer(transfer);
        //creates transfer record for reciever
        transferDao.createTransferToSender(transfer);

    }
    @RequestMapping(path="/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int transferId) throws Exception {
        Transfer transfer = new Transfer();
        //saves attributes to a object that represent a user from the database
        transfer = transferDao.getTransferById(transferId);
        //returns object with that username attributes
        return transfer;
    }
}
