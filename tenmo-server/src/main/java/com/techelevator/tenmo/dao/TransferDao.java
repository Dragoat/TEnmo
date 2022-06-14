package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public interface TransferDao {

    String getTransferType(int transferId);

    void addToReceiverBalance(int userId, BigDecimal amount);

    void  subtractFromSenderBalance(int userId, BigDecimal amount);

    boolean createTransfer(Transfer transfer ) throws Exception;

    boolean checkBalanceBeforeTransfer(BigDecimal balance, BigDecimal amount);

    boolean notZeroOrNegative();

    List<Transfer> getTransfersList(int id) throws Exception;

    Transfer getTransferById(int transferId) throws Exception;

    BigDecimal getBalance(int userId);

    int getSetAccountFromId(int accountFromId);





}