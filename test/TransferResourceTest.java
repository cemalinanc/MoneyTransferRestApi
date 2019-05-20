import com.inancc.restapi.transfer.TransferResource;
import db.ApplicationDatabase;
import model.Account;
import model.Transfer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;
import service.ServiceMessages;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TransferResourceTest extends JerseyTest {

    private final String TEST_ACCOUNT_ID1 = "testAcc1";
    private final String TEST_ACCOUNT_ID2 = "testAcc2";
    private final String INVALID_ACCOUNT_ID = "invalidAcc";

    private final double ACCOUNT1_INIT_BALANCE = 100;
    private final double ACCOUNT2_INIT_BALANCE = 200;
    private final double AMOUNT1 = 50;
    private final double AMOUNT2 = 100;




    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        confugureTestDb();
        return new ResourceConfig(TransferResource.class);
    }


    private void confugureTestDb(){

        Account account1 = new Account();
        account1.setBalance(ACCOUNT1_INIT_BALANCE);
        account1.setAccountId(TEST_ACCOUNT_ID1);

        Account account2 = new Account();
        account2.setBalance(ACCOUNT2_INIT_BALANCE);
        account2.setAccountId(TEST_ACCOUNT_ID2);

        ApplicationDatabase.addAccount(account1);
        ApplicationDatabase.addAccount(account2);
    }




    @Test
    public void testTransferPostRequest(){

        Transfer transfer = new Transfer();
        transfer.setId("1");
        transfer.setSourceAccountId(TEST_ACCOUNT_ID1);
        transfer.setDestAccountId(TEST_ACCOUNT_ID2);
        transfer.setAmount(AMOUNT1);

        Response response =  target("/transfers").request().post(Entity.entity(transfer, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Transfer responseTransfer = (Transfer) response.readEntity(Transfer.class);
        assertTransfer(transfer, responseTransfer, ServiceMessages.EXECUTED);


        // clean transferStatus
        transfer.setStatus("");
        transfer.setAmount(AMOUNT2);
        response =  target("/transfers").request().post(Entity.entity(transfer, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
        responseTransfer = (Transfer) response.readEntity(Transfer.class);
        assertTransfer(transfer, responseTransfer, ServiceMessages.INSUFFICENT_BALANCE);

    }



    @Test
    public void testInvalidAccountRequest() {
        Transfer transfer = new Transfer();
        transfer.setId("1");
        transfer.setSourceAccountId(TEST_ACCOUNT_ID1);
        transfer.setDestAccountId(INVALID_ACCOUNT_ID);
        transfer.setAmount(AMOUNT1);

        Response response =  target("/transfers").request().post(Entity.entity(transfer, MediaType.APPLICATION_JSON_TYPE));
        Assert.assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
        Transfer responseTransfer = (Transfer) response.readEntity(Transfer.class);
        assertTransfer(transfer, responseTransfer, ServiceMessages.INVALID_ACCOUNT);
    }



    private void assertTransfer(Transfer requestedTransfer, Transfer responseTransfer, String serviceStatus){

        Assert.assertEquals(requestedTransfer.getAmount(), responseTransfer.getAmount(), 0.0d);
        Assert.assertEquals(requestedTransfer.getDestAccountId(), responseTransfer.getDestAccountId());
        Assert.assertEquals(requestedTransfer.getSourceAccountId(), responseTransfer.getSourceAccountId());
        Assert.assertEquals(responseTransfer.getStatus(), serviceStatus);
        Assert.assertEquals(requestedTransfer.getId(), responseTransfer.getId());
    }

}
