package service;

import db.ApplicationDatabase;
import model.Account;



public class DebitAccountService implements AccountService {


    @Override
    public Account getAccount(String accountId) {

        Account account = ApplicationDatabase.getAccount(accountId);

        if(account == null){
            System.out.println("Account not found with id: " + accountId);
        }

        return account;

    }


    public String deposit(String accountId, double amount ){

        Account account = getAccount(accountId);
        account.setBalance(account.getBalance() + amount);

        ApplicationDatabase.updateAccount(accountId, account);
        return  ServiceMessages.EXECUTED;
    }


    public String withdraw(String accountId, double amount ){

        String status = ServiceMessages.REQUESTED;
        Account account = getAccount(accountId);

        if(account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            ApplicationDatabase.updateAccount(accountId, account);
            status = ServiceMessages.EXECUTED;
        }
        else{
            status = ServiceMessages.INSUFFICENT_BALANCE;
        }

        return  status;
    }


}
