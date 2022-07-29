package bankPack;

import bankPack.bankExceptions.BadLoanIndexException;

import java.io.Serializable;
import java.util.ArrayList;

public class Bank implements Serializable {
    public enum CTYPE {
        NUMERO, NOM
    }
    private ArrayList<BankAccount> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public boolean makeALoan(String accountNumber, int amount, int mensuality) {
        boolean result = true;
        Loan l;
        BankAccount account = getAccount(accountNumber);
        if (account instanceof SparingAccount) {
            result = false;
        } else {
            NormalAccount bac = (NormalAccount) account;
            l = new Loan(amount, mensuality, bac);
            bac.addALoan(l);
        }

        return result;
    }

    public void operateADepository(String accountNumber, int amount) {
        getAccount(accountNumber).deposit(amount);
    }

    public boolean operateAWithDraw(String accountNumber, int amount) {
        return getAccount(accountNumber).withDraw(amount);
    }

    public boolean operateLoanPayment(String accountNumber, int loanIdx) throws BadLoanIndexException {
        BankAccount account = getAccount(accountNumber);
        if (account instanceof SparingAccount)
            return false;

        NormalAccount bac = (NormalAccount)account;
        bac.payLoan(loanIdx);
        return true;
    }

    public BankAccount getAccount(String number) {
        for (int index = 0; index < accounts.size(); index++) {
            if (accounts.get(index).getNumber().equals(number)) {
                return accounts.get(index);
            }
        }
        return null;
    }

    public ArrayList<BankAccount> getListBeginingBy(String str, CTYPE type, boolean regex) {
        ArrayList<BankAccount> newListe = new ArrayList<>();
        str = str.toLowerCase();
        if (type == CTYPE.NOM) {
            for (BankAccount bk : this.accounts) {
                if (bk.getOwner().toLowerCase().matches(regex ? str : ("^" + str + "[a-z]*"))) {
                    newListe.add(bk);
                }
            }
        } else if (type == CTYPE.NUMERO) {
            for (BankAccount bk : this.accounts) {
                if (bk.getNumber().toLowerCase().matches(regex ? str : ("^" + str + "[a-z0-9]*"))) {
                    newListe.add(bk);
                }
            }
        }
        return newListe;
    }

    public int getAccountIndex(String number) {
        for (int index = 0; index < accounts.size(); index++) {
            if (accounts.get(index).getNumber().equals(number)) {
                return index;
            }
        }
        return -1;
    }

    public ArrayList<BankAccount> getAccounts() {
        return this.accounts;
    }

    public boolean isANormalAccount(int index) {
        return (this.accounts.get(index) instanceof NormalAccount);
    }

    public boolean isASparingAccount(int index) {
        return (this.accounts.get(index) instanceof SparingAccount);
    }
}
