package service;

import model.Account;
import model.Transfer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MoneyTransferService implements TransferService {


    //@Inject
    private static AccountService accountService ;
    private static Object transferLock = new Object();

    @Inject
    public MoneyTransferService(AccountService accountService){
        this.accountService = accountService;

    }


    @Override
    public Transfer transfer(Transfer transfer) {

        Account sourceAccount = accountService.getAccount(transfer.getSourceAccountId());
        Account destAccount = accountService.getAccount(transfer.getDestAccountId());
        double amount = transfer.getAmount();

        String status = validateAccounts(sourceAccount, destAccount);

        if(status.equals(ServiceMessages.VALID)) {

            synchronized (transferLock) {
                status = accountService.withdraw(sourceAccount.getAccountId(), amount);

                if (status.equals(ServiceMessages.EXECUTED)) {
                    status = accountService.deposit(destAccount.getAccountId(), amount);
                }
            }
        }

        transfer.setStatus(status);
        return transfer;
    }



    private String validateAccounts(Account account1, Account account2){

        if(account1 == null || account2 == null )
            return ServiceMessages.INVALID_ACCOUNT;

        return ServiceMessages.VALID;
    }


}
