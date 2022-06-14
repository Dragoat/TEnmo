package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private BigDecimal amount;
    private int senderId;
    private int receiverId;
    private String transferType;
    private int transferTypeId;
    private int accountFrom;
    private int accountTo;
    private int transferStatusId;
    private String usernameTo;
    private String usernameFrom;
    private String transferTypeDesc;
    private String transferStatusDesc;

    public Transfer(){}

    public Transfer(int transferId, BigDecimal amount, int senderId, int receiverId, String transferType,
                    int transferTypeId, int accountFrom, int accountTo, int transferStatusId, String usernameTo,
                    String usernameFrom, String transferTypeDesc, String transferStatusDesc) {

        this.transferId = transferId;
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transferType = transferType;
        this.transferTypeId = transferTypeId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.transferStatusId = transferStatusId;
        this.usernameTo = usernameTo;
        this.usernameFrom = usernameFrom;
        this.transferTypeDesc = transferTypeDesc;
        this.transferStatusDesc = transferStatusDesc;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", amount=" + amount +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", transferType='" + transferType + '\'' +
                ", transferTypeId=" + transferTypeId +
                ", accountFromId=" + accountFrom +
                ", accountToId=" + accountTo +
                '}';
    }

    // Setters & Getters

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String tranferStatusDesc) {
        this.transferStatusDesc = tranferStatusDesc;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferStatus(int transferStatusId){ this.transferStatusId = transferStatusId;}

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void getTransferTypeId(int transferTypeId){
        this.transferTypeId = transferTypeId;
    }

    public void setTransferTypeId(int transfer_type_id) {
    }

    public int getaccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFromId) {
        this.accountFrom = accountFromId;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountToId) {
        this.accountTo = accountToId;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }
}
