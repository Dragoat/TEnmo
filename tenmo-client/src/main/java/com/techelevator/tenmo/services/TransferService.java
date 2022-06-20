package com.techelevator.tenmo.services;

import com.techelevator.tenmo.ExceptionClientHandler;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TransferService {
    public static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    //Authorization Token variable.
    private String authToken = null;

    //setter for Authorization Token
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    //sends the server the request to send money.
    public String sendMoney(Transfer transfer) {

        String success = "\nYour transaction was successful.";
        //try to get the response from the server.
        try {
            restTemplate.exchange(API_BASE_URL + "transfers/", HttpMethod.PUT, makeTransferEntity(transfer), Void.class);

        }//catch exception if server error response or I/O exception occurs.
        catch (RestClientResponseException | ResourceAccessException e ) {
            BasicLogger.log(e.getMessage());

        }
        //Prints and returns message of successful payments transfer
        System.out.println(success);
        return success;
    }

    //sends the server the request to gets the Transfer list.
    public Transfer[] getTransferList(){
        Transfer[] transfers = null;
        //try to get the response from the server.
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } //catch exception if server error response or I/O exception occurs.
        catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        //returns the response.
        return transfers;
    }

    //sends the server the request to gets the TransferId.
    public Transfer getTransferById(int transferId){
        Transfer transfer = new Transfer();
        try{
            //try to get the response from the server.
            ResponseEntity< Transfer > response = restTemplate.exchange(API_BASE_URL + "transfers/" + transferId , HttpMethod.GET, makeAuthEntity(), Transfer.class );
            transfer = response.getBody();
        }
        //catch exception if server error response or I/O exception occurs.
        catch (RestClientResponseException | ResourceAccessException e ) {
            BasicLogger.log(e.getMessage());
        }
        //returns the transferId and object info.
        return transfer;
    }


    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    public BigDecimal getBalance(){
        BigDecimal balance = null;
        try{
            ResponseEntity< BigDecimal > response = restTemplate.exchange(API_BASE_URL + "account/balance" , HttpMethod.GET, makeAuthEntity(), BigDecimal.class );
            balance = response.getBody();
        }
        catch (RestClientResponseException | ResourceAccessException e ) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }




    private HttpEntity<Void> makeAuthEntity () {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }










}

