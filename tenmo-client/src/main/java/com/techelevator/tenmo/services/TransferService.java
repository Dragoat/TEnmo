package com.techelevator.tenmo.services;

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

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String sendMoney(Transfer transfer) {

        String success = "\nYour transaction was successful.";
        try {
            restTemplate.exchange(API_BASE_URL + "transfers/", HttpMethod.PUT, makeTransferEntity(transfer), Void.class);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());

        }
        System.out.println(success);
        return success;
    }
    public Transfer[] getTransferList(){
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }
    public Transfer getTransferById(int transferId){
        Transfer transfer = new Transfer();
        try{
            ResponseEntity< Transfer > response = restTemplate.exchange(API_BASE_URL + "transfers/" + transferId , HttpMethod.GET, makeAuthEntity(), Transfer.class );
            transfer = response.getBody();
        }
        catch (RestClientResponseException | ResourceAccessException e ) {
            BasicLogger.log(e.getMessage());
        }
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
            ResponseEntity< BigDecimal > response = restTemplate.exchange(API_BASE_URL + "accounts" , HttpMethod.GET, makeAuthEntity(), BigDecimal.class );
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

