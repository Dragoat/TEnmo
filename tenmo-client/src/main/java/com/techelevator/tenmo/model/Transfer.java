package com.techelevator.tenmo.model;

import java.math.BigDecimal;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private BigDecimal amount;
    private int senderId;
    private int receiverId;
    private String transferType;
    private String transferTypeDesc;
    private String transferStatusDesc;
    private String usernameFrom;
    private String usernameTo;

    public Transfer(int transferId, BigDecimal amount, int senderId, int receiverId, String transferType, String transferTypeDesc, String transferStatusDesc, String usernameFrom, String usernameTo) {
        this.transferId = transferId;
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.transferType = transferType;
        this.transferTypeDesc = transferTypeDesc;
        this.transferStatusDesc = transferStatusDesc;
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
    }

    public Transfer(){}



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


    public BigDecimal getAmountToReciver() {
        return amount;
    }//added

    public void setAmountToSender(BigDecimal amount) {//added
        this.amount = amount;
    }

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
    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }


    @Override
    public String toString(){ // to string for the transfer list
        return getTransferId()  + "                 To: "   + getUsernameFrom()+ "/" + getUsernameTo()+  "                 $" + getAmount();
    }

    public String toStringForTransferDetails(){ // to string for view transfer details
        return "ID:      " + getTransferId() + "\nFrom:    " + getUsernameFrom() + "\nTo:      " + getUsernameTo() + "\nType:    " + getTransferTypeDesc() + "\nStatus:  " + getTransferStatusDesc()
                + "\nAmount:  " + getAmount();
    }
}

