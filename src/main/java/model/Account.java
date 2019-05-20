package model;


public class Account {

    private String accountId;
    private double balance;


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object other){

        double delta = 0.000000000001;

        Account otherAccount = (Account)other;
        return this.accountId.equals(otherAccount.accountId) && ( Math.abs(this.balance - otherAccount.balance )<= delta) ;
    }

    @Override
    public int hashCode(){
        int hashCode = 31 * accountId.hashCode();
        hashCode = hashCode + 31 * Double.hashCode(balance);

        return hashCode;
    }


    public Account deepCopy(){

        Account account = new Account();
        account.setAccountId(this.accountId);
        account.setBalance(this.balance);

        return account;
    }

}
