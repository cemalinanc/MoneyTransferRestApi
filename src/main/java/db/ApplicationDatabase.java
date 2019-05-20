package db;

import model.Account;
import model.Transfer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationDatabase {

    private static Map<String, Account> accountMap = new ConcurrentHashMap<>();

    static
    {
        Account account1 = new Account();
        account1.setAccountId("1");
        account1.setBalance(100);
        accountMap.put(account1.getAccountId(), account1);

        Account account2 = new Account();
        account2.setAccountId("2");
        account2.setBalance(200);
        accountMap.put(account2.getAccountId(), account2);
    }

    public static Account getAccount(String accountId){
        return accountMap.get(accountId);
    }

    public static void updateAccount(String accountId, Account account){
        accountMap.put(accountId, account);
    }


    public static void addAccount(Account account){
        accountMap.put(account.getAccountId(), account.deepCopy());
    }


}
