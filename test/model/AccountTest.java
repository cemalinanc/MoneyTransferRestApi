package model;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    @Test
    public void testDeepCopy(){

        Account account1 = new Account();
        account1.setAccountId("1");
        account1.setBalance(100);

        Account account2 = account1.deepCopy();
        account2.setBalance(200);
        account2.setAccountId("2");

        Assert.assertFalse(account1.equals(account2));
    }

    @Test
    public void testAccountEqualAfterDeepCopy(){

        Account account1 = new Account();
        account1.setAccountId("1");
        account1.setBalance(100);

        Account account2 = account1.deepCopy();
        Assert.assertTrue(account1.equals(account2));

    }

    @Test
    public void testAccountEqual(){

        Account account1 = new Account();
        account1.setAccountId("1");
        account1.setBalance(100);

        Account account2 = new Account();
        account2.setAccountId("1");
        account2.setBalance(100);

        Assert.assertTrue(account1.equals(account2));
        Assert.assertTrue(account1.hashCode() == account2.hashCode());

    }

    @Test
    public void testAccountNotEqual(){
        Account account1 = new Account();
        account1.setAccountId("1");
        account1.setBalance(100);

        Account account2 = new Account();
        account2.setBalance(200);
        account2.setAccountId("1");

        Assert.assertFalse(account1.equals(account2));

        account2.setBalance(100);
        account2.setAccountId("2");
        Assert.assertFalse(account1.equals(account2));


    }

}
