package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.tenmo.services.TransferService;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final TransferService transferService = new TransferService();
    private AuthenticatedUser currentUser; //when make a call to server, use this person with token
    TenmoService tenmoService = new TenmoService();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
        else{
            //gets the unique Authorization token generated in the registration of the user.
            tenmoService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        //getsBalance from the service response
        System.out.println(transferService.getBalance());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        showTransactionList();
        //checks if id entered is a integer
        int transferId = consoleService.promptForInt("Enter transfer id to view transaction (0 to cancel): ");
        //method to select the information to view from the transferId.
        selection(transferId);

	}

    private void sendBucks() {
        // TODO Auto-generated method stub
        //creates array with existing user and displays them.
        User existingUsers[] = AllUserList();
        //gives the user the option to insert the userId in order to send money to existing user.
        String str1 = "\nEnter user id to request money from (0 to cancel): ";
        selectUserToTransfer(existingUsers,str1);

    }


    //method not finished.
    private void requestBucks() {
        // TODO Auto-generated method stub
        User existingUsers[] = AllUserList();
        Scanner scanner = new Scanner(System.in);
        String UserIdRequest = scanner.nextLine();
        int RequestedUser = consoleService.promptForInt(UserIdRequest);
        String str1 = "\nEnter user id to send money to (0 to cancel): ";
        selectUserToTransfer(existingUsers, str1);

    }






    //Displays list of transfer
    private void showTransactionList(){
        Transfer[] transferList = transferService.getTransferList();
        consoleService.printTransactionHeader();
        for (Transfer eachTransfer : transferList) {
            System.out.println(eachTransfer.toString());
        }
    }




    public void selection(int transferId) {
        consoleService.printTransactionHeaderBottom();
        //checks the transfer id input is not zero
        if (transferId == 0) {
            consoleService.printMainMenu();
        }
        //if transfer Id anything except zero
        else {
            //displays UI.
            consoleService.printTransactionDetailsHeader();
            //sends transferId to get a ServerResponse.
            Transfer transfer = new Transfer();
            transfer = transferService.getTransferById(transferId);
            //give info about the the user with that transferId
            System.out.println(transfer.toStringForTransferDetails());
            //display UI
            consoleService.printTransactionHeaderBottom();
        }
    }



    //Displays list of user
    public User[] AllUserList() {
        //get List of user from the response of the server
        User[] userList = tenmoService.getAllUsersForSendingMoney();
        //display UI
        consoleService.printSendTEBucksHeader();
        //prints and formats users.
        for (User eachUser : userList) {
            System.out.println(eachUser.toString());
        }
        //returns the user list
        return userList;

    }


    public void selectUserToTransfer(User[] userList, String str1) {
        boolean IsmatchId = false;
        //displays UI.
        consoleService.printTransactionHeaderBottom();
        //if IsmatchId is true at the end of the loop breaks out of the loop.
        while (!IsmatchId) {
            //get user id and convert it to int.
            int userToId = consoleService.promptForInt(str1);

            //checks list of user
            for (User user : userList) {
                //checks that id of the current user is not the same as select user.
                if (user.getId() == userToId && userToId != 0) {
                    //checks that userName is not the same currentUserName.
                    if (!user.getUsername().equals(currentUser)) {
                        IsmatchId = true;
                    }
                }
                //if user is zero breaks out of the loop to main menu.
                else if (userToId == 0){
                    consoleService.printMainMenu();
                    return; //break;
                }
            }

            //Procceds with Transaction if user id is different and userName is different
            if (IsmatchId) {
                //ask user for transfer amount and checks if amount is valid.
                double inputAmount = consoleService.promptForDouble("\nEnter Dollar amount including decimal: $");
                //amount is less or zero.
                if(inputAmount <= 0){
                    //tells user error message and returns to the menu.
                    System.out.println("Please enter a amount larger than zero");
                    IsmatchId = true;
                    break;
                }
                //convert amount to BigDecimal
                BigDecimal transferAmount = BigDecimal.valueOf(inputAmount);
                Transfer transfer = new Transfer();
                //sets amount to Sender
                transfer.setAmount(transferAmount);
                //sets SenderId
                transfer.setReceiverId(userToId);


                //setAmount to Reciever
                transfer.setAmountToSender(transferAmount);
                //set Id to Reciever
                transfer.setSenderId(Math.toIntExact(currentUser.getUser().getId()));//added

                //gives the info for the Server in order to transfer money
                transferService.sendMoney(transfer);

                //if user not founds prints message and returns to selection.
            } else {
                System.out.println("User ID is not found");
                break;
            }
        }

    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub
    }






}
