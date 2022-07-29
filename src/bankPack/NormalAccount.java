package bankPack;

import bankPack.bankExceptions.BadLoanIndexException;

import java.util.ArrayList;
import java.util.ListIterator;

public class NormalAccount extends BankAccount {
    private ArrayList<Loan> loans;

    public NormalAccount(String owner, String number, int solde, double interest) {
        this.owner = owner;
        this.number = number;
        this.solde = solde;
        this.interest = interest;
        this.loans = new ArrayList<>();
    }

    public void addALoan(Loan l) {
        this.loans.add(l);
    }

    public boolean payYourLoans() {
        boolean out = true;
        for (int i = 0; i < this.loans.size(); i++) {
            out = !out ? false : this.loans.get(i).reimbourse();
        }
        return out;
    }

    public boolean payLoan(int idx) throws BadLoanIndexException {
        if (idx < 0 || idx >= this.loans.size()) {
            throw new BadLoanIndexException("Cet id de pret n'existe pas pour ce compte");
        }
        return this.loans.get(idx).reimbourse();
    }

    public boolean isReimbursed(int loanIdx) throws BadLoanIndexException {
        if (loanIdx < 0 || loanIdx >= this.loans.size()) {
            throw new BadLoanIndexException("Cet id de pret n'existe pas pour ce compte");
        }
        return (this.loans.get(loanIdx).isReimbursed());
    }

    @Override
    public int calculInterest() {
        return (int) (this.solde * this.interest);
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }
}
