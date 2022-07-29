package bankPack;

import java.io.Serializable;

public abstract class BankAccount implements Serializable {
    protected String owner;
    protected String number;
    protected int solde;
    protected double interest;

    public boolean deposit(int amount)
    {
        if (amount > 0)
        {
            this.solde += amount;
            return true;
        }
        return false;
    }

    public boolean withDraw(int amount)
    {
        if (amount > 0 && amount <= this.solde)
        {
            this.solde -= amount;
            return true;
        }
        return false;
    }

    //Abstracts
    public abstract int calculInterest();

    //Getter and Setter
    public String getOwner() {
        return owner;
    }

    public String getNumber() {
        return number;
    }

    public int getSolde() {
        return solde;
    }

    public double getInterest() {
        return interest;
    }
}
