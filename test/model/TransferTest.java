package model;

import org.junit.Assert;
import org.junit.Test;
import service.ServiceMessages;
import service.TransferService;

public class TransferTest {

    @Test
    public void testEquals(){

        Transfer transfer1 = new Transfer();
        transfer1.setAmount(100);
        transfer1.setDestAccountId("2");
        transfer1.setSourceAccountId("1");
        transfer1.setStatus(ServiceMessages.VALID);
        transfer1.setId("1");

        Transfer transfer2 = new Transfer();
        transfer2.setAmount(100);
        transfer2.setDestAccountId("2");
        transfer2.setSourceAccountId("1");
        transfer2.setStatus(ServiceMessages.VALID);
        transfer2.setId("1");

        Assert.assertTrue(transfer1.equals(transfer2));
        Assert.assertTrue(transfer1.hashCode() == transfer2.hashCode());
    }


    @Test
    public void testNotEqual(){
        Transfer transfer1 = new Transfer();
        transfer1.setAmount(100);
        transfer1.setDestAccountId("2");
        transfer1.setSourceAccountId("1");
        transfer1.setStatus(ServiceMessages.VALID);
        transfer1.setId("1");

        Transfer transfer2 = new Transfer();
        transfer2.setAmount(200);
        transfer2.setDestAccountId("2");
        transfer2.setSourceAccountId("1");
        transfer2.setStatus(ServiceMessages.VALID);
        transfer2.setId("1");

        Assert.assertFalse(transfer1.equals(transfer2));

        transfer2.setAmount(100);
        transfer2.setDestAccountId("1");
        Assert.assertFalse(transfer1.equals(transfer2));

        transfer2.setDestAccountId("2");
        transfer2.setSourceAccountId("2");
        Assert.assertFalse(transfer1.equals(transfer2));

        transfer2.setSourceAccountId("1");
        transfer2.setStatus(ServiceMessages.INSUFFICENT_BALANCE);
        Assert.assertFalse(transfer1.equals(transfer2));

        transfer2.setStatus(ServiceMessages.VALID);
        transfer2.setId("2");
        Assert.assertFalse(transfer1.equals(transfer2));
    }
}
