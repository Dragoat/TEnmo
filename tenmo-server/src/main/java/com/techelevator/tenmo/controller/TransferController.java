package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void depositMoney(BigDecimal amount, int id){
        accountDao.getAccount(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Transfer> getListOfTransfers(Principal principal) throws Exception {
        int userId = userDao.findIdByUsername(principal.getName());
        return transferDao.getTransfersList(userId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void withdrawFromSender(@RequestBody Transfer transfer, Principal principal) throws Exception{
        int senderId =  userDao.findIdByUsername(principal.getName());
        transfer.setSenderId(userDao.findIdByUsername(principal.getName()));
        transferDao.subtractFromSenderBalance(senderId, transfer.getAmount() );
        transferDao.addToReceiverBalance(transfer.getReceiverId(), transfer.getAmount());
        transferDao.createTransfer(transfer);
    }
    @RequestMapping(path="/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int transferId) throws Exception {
        Transfer transfer = new Transfer();
        transfer = transferDao.getTransferById(transferId);
        return transfer;
    }
}
