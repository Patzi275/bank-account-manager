package bankPack;

public class SparingAccount extends BankAccount {
    public SparingAccount(String owner, String number, int solde, double interest) {
        this.owner = owner;
        this.number = number;
        this.solde = solde;
        this.interest = interest;
    }

    @Override
    public int calculInterest() {
        return (int) (this.interest * this.solde);
    }
}
