package service;

import model.Account;
import model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.when;

public class MoneyTransferServiceTest {

    private TransferService transferService;
    private final String ACCOUNT_ID_1 = "testAccount1";
    private final String ACCOUNT_ID_2 = "testAccount2";
    private final String INVALID_ACCOUNT_ID = "invalidAccountId1";


    private final double BALANCE_1 = 100;
    private final double BALANCE_2 = 200;
    private final double TRANSFER_AMOUNT = 50;

    private Account account;
    private Account account2;


    @Before
    public void setUp(){

        account = new Account();
        account.setAccountId(ACCOUNT_ID_1);
        account.setBalance(BALANCE_1);

        account2 = new Account();
        account2.setAccountId(ACCOUNT_ID_2);
        account2.setBalance(BALANCE_2);

        AccountService accountService = Mockito.mock(DebitAccountService.class);
        when(accountService.getAccount(ACCOUNT_ID_1)).thenReturn(account);
        when(accountService.getAccount(ACCOUNT_ID_2)).thenReturn(account2);
        when(accountService.getAccount(INVALID_ACCOUNT_ID)).thenReturn(null);

        when(accountService.withdraw(ACCOUNT_ID_1, BALANCE_2)).thenReturn(ServiceMessages.INSUFFICENT_BALANCE);
        when(accountService.withdraw(ACCOUNT_ID_1, BALANCE_1)).thenReturn(ServiceMessages.EXECUTED);
        when(accountService.deposit(ACCOUNT_ID_2, BALANCE_1)).thenReturn(ServiceMessages.EXECUTED);

        Answer withdrawFromAcc1 = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                double balance = account.getBalance() - TRANSFER_AMOUNT;
                account.setBalance( balance);
                return ServiceMessages.EXECUTED;
            }
        };

        Mockito.doAnswer(withdrawFromAcc1).when(accountService).withdraw(ACCOUNT_ID_1, TRANSFER_AMOUNT);


        Answer depositToAcc2 = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                double balance = account2.getBalance() + TRANSFER_AMOUNT;
                account2.setBalance( balance);
                return ServiceMessages.EXECUTED;
            }
        };

        Mockito.doAnswer(depositToAcc2).when(accountService).deposit(ACCOUNT_ID_2, TRANSFER_AMOUNT);


        transferService = new MoneyTransferService(accountService);

    }





    @Test
    public void testInsufficentBalance(){

        Transfer transfer = new Transfer();
        transfer.setId("1");
        transfer.setSourceAccountId(ACCOUNT_ID_1);
        transfer.setDestAccountId(ACCOUNT_ID_2);
        transfer.setAmount(200);


        Transfer transferAfterRequest = transferService.transfer(transfer);

        Assert.assertEquals(transferAfterRequest.getStatus(), ServiceMessages.INSUFFICENT_BALANCE);
        Assert.assertEquals(transferAfterRequest.getSourceAccountId(), ACCOUNT_ID_1);
        Assert.assertEquals(transferAfterRequest.getDestAccountId(), ACCOUNT_ID_2);
        Assert.assertEquals(transferAfterRequest.getAmount(), BALANCE_2, 0.00000001);

    }



    @Test
    public void testTransfer(){

        Transfer transfer = new Transfer();
        transfer.setId("1");
        transfer.setSourceAccountId(ACCOUNT_ID_1);
        transfer.setDestAccountId(ACCOUNT_ID_2);
        transfer.setAmount(TRANSFER_AMOUNT);


        Transfer transferAfterRequest = transferService.transfer(transfer);

        Assert.assertEquals(transferAfterRequest.getStatus(), ServiceMessages.EXECUTED);
        Assert.assertEquals(transferAfterRequest.getSourceAccountId(), ACCOUNT_ID_1);
        Assert.assertEquals(transferAfterRequest.getDestAccountId(), ACCOUNT_ID_2);
        Assert.assertEquals(transferAfterRequest.getAmount(), TRANSFER_AMOUNT, 0.00000001);
        Assert.assertEquals(BALANCE_1-TRANSFER_AMOUNT, account.getBalance(), 0.0000001);
        Assert.assertEquals(BALANCE_2+TRANSFER_AMOUNT, account2.getBalance(),  0.0000001);

    }



    @Test
    public void testInvalidSourceAccount(){

        Transfer transfer = new Transfer();
        transfer.setId("1");
        transfer.setSourceAccountId(INVALID_ACCOUNT_ID);
        transfer.setDestAccountId(ACCOUNT_ID_2);
        transfer.setAmount(10);

        Transfer transferAfterRequest = transferService.transfer(transfer);

        Assert.assertEquals(transferAfterRequest.getStatus(), ServiceMessages.INVALID_ACCOUNT);
        Assert.assertEquals(transferAfterRequest.getSourceAccountId(), INVALID_ACCOUNT_ID);
        Assert.assertEquals(transferAfterRequest.getDestAccountId(), ACCOUNT_ID_2);
        Assert.assertEquals(transferAfterRequest.getAmount(), 10, 0.00000001);
    }


    @Test
    public void testInvalidDestAccount(){

        Transfer transfer = new Transfer();
        transfer.setId("1");
        transfer.setSourceAccountId(ACCOUNT_ID_1);
        transfer.setDestAccountId(INVALID_ACCOUNT_ID);
        transfer.setAmount(10);

        Transfer transferAfterRequest = transferService.transfer(transfer);

        Assert.assertEquals(transferAfterRequest.getStatus(), ServiceMessages.INVALID_ACCOUNT);
        Assert.assertEquals(transferAfterRequest.getSourceAccountId(), ACCOUNT_ID_1);
        Assert.assertEquals(transferAfterRequest.getDestAccountId(), INVALID_ACCOUNT_ID);
        Assert.assertEquals(transferAfterRequest.getAmount(), 10, 0.0);
    }







}
