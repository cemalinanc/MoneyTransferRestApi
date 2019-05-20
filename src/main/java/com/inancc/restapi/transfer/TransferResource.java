package com.inancc.restapi.transfer;



import model.Transfer;
import service.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/transfers")
@Consumes(MediaType.APPLICATION_JSON)
public class TransferResource {

   // @Inject
    TransferService transferService = new MoneyTransferService(new DebitAccountService());



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transferMoney(Transfer transfer){

        Transfer executedTransfer = transferService.transfer(transfer);
        return createResponse(executedTransfer);
    }


    private Response createResponse(Transfer transfer){

        String transferStatus = transfer.getStatus();


        if(transferStatus.equals(ServiceMessages.EXECUTED)){
            return Response.status(Response.Status.OK)
                    .entity(transfer)
                    .build();
        }

        else{
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(transfer)
                    .build();
        }

    }
}
