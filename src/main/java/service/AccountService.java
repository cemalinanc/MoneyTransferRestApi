package service;

import model.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account getAccount(String accountId);
    String deposit(String accountId, double balance);
    String withdraw(String accountId, double balance);



}
