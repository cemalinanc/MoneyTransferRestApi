package model;




public class Transfer {

    private String id;
    private String sourceAccountId;
    private String destAccountId;
    private double amount;
    private String status;




    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestAccountId() {
        return destAccountId;
    }

    public void setDestAccountId(String destAccountId) {
        this.destAccountId = destAccountId;
    }


    @Override
    public boolean equals(Object other){

        Transfer otherTransfer = (Transfer) other;

        if(!this.id.equals(otherTransfer.id))
            return false;

        if(!this.sourceAccountId.equals(otherTransfer.sourceAccountId))
            return false;

        if(!this.destAccountId.equals(otherTransfer.destAccountId))
            return false;

        if(this.amount != otherTransfer.amount)
            return false;

        if(!this.status.equals(otherTransfer.status))
            return false;

        return true;
    }

    @Override
    public int hashCode(){
        int hashCode = 31 * id.hashCode();
        hashCode *= 31 + sourceAccountId.hashCode();
        hashCode *= 31 + destAccountId.hashCode();
        hashCode *= 31 + Double.hashCode(amount);
        hashCode *= 31 + status.hashCode();

        return hashCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
