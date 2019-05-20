package service;

import db.ApplicationDatabase;
import model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class DebitAccountServiceTest {

    private AccountService accountService = new DebitAccountService();
    private Account account1;
    private Account account2;

    private final String TEST_ACCOUNT_ID_1 = "testAccount1";
    private final String TEST_ACCOUNT_ID_2 = "testAccount2";
    private final double INIT_BALANCE_1 = 100;
    private final double INIT_BALANCE_2 = 200;


    @Before
    public void setUp() throws Exception{

        account1 = new Account();
        account1.setAccountId(TEST_ACCOUNT_ID_1);
        account1.setBalance(INIT_BALANCE_1);

        account2 = new Account();
        account2.setAccountId(TEST_ACCOUNT_ID_2);
        account2.setBalance(INIT_BALANCE_2);

        ApplicationDatabase.addAccount(account1);
        ApplicationDatabase.addAccount(account2);
    }

    @Test
    public void getAccountTest(){

        Account account = accountService.getAccount(TEST_ACCOUNT_ID_1);
        assertAccountEqual(account, TEST_ACCOUNT_ID_1, INIT_BALANCE_1);

        account = accountService.getAccount(TEST_ACCOUNT_ID_2);
        assertAccountEqual(account, TEST_ACCOUNT_ID_2, INIT_BALANCE_2);

    }



    @Test
    public void withdrawTest(){

        double amount = 50.0d;
        accountService.withdraw(TEST_ACCOUNT_ID_1, amount);

        assertAccount(TEST_ACCOUNT_ID_1, INIT_BALANCE_1-amount);

    }


    @Test
    public void depositTest(){

        double amount = 50.0d;
        accountService.deposit(TEST_ACCOUNT_ID_1, amount);

        assertAccount(TEST_ACCOUNT_ID_1, INIT_BALANCE_1 + amount);

    }


    private void assertAccount(String accountId, double exptBalance){

        Account account = ApplicationDatabase.getAccount(accountId);
        Assert.assertEquals(accountId, account.getAccountId());
        Assert.assertEquals(exptBalance, account.getBalance(), 0.000000000001);
    }



    private void assertAccountEqual(Account account, String expAccountId, double expBalance){
        Assert.assertEquals(account.getAccountId(), expAccountId);
        Assert.assertEquals(account.getBalance(), expBalance, 0.0000000000001);
    }


}
