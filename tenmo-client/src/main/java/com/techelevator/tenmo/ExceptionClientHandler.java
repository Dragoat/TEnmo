package com.techelevator.tenmo;

public class ExceptionClientHandler extends Exception {

    private String message;

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
