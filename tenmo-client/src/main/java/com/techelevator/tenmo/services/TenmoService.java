package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


public class TenmoService {

    public static final String API_BASE_URL = "http://localhost:8080/" ;
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    //sends request to server to get Balance
    public BigDecimal getBalance(){
        BigDecimal balance = null;
        //try to get Response
        try{
            ResponseEntity< BigDecimal > response = restTemplate.exchange(API_BASE_URL + "accounts" , HttpMethod.GET, makeAuthEntity(), BigDecimal.class );
            balance = response.getBody();
        }//catch exception if server error response or I/O exception occurs.
        catch (RestClientResponseException | ResourceAccessException e ) {
            BasicLogger.log(e.getMessage());
        }
        //returns the balance.
        return balance;
    }

    //sends request to server to retrieve all user
    public User[] getAllUsersForSendingMoney(){
        User[] users = null;
        //try to get Response
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "account/users", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        }//catch exception if server error response or I/O exception occurs.
        catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        //returns list of users.
        return users;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
