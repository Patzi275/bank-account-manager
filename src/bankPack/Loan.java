package bankPack;

import java.io.Serializable;

public class Loan implements Serializable {
    private int amount, mensuality;
    private boolean paid;
    private NormalAccount account;

    public Loan(int amount, int mensuality, NormalAccount account) {
        this.amount = amount;
        this.mensuality = mensuality;
        this.account = account;
        paid = false;
        account.deposit(amount);
    }

    public boolean reimbourse() {
        if (this.account.withDraw(this.amount)) {
            this.paid = true;
            return true;
        }
        else
            return false;
    }

    public int getAmount() {
        return amount;
    }

    public int getMensuality() {
        return mensuality;
    }

    public boolean isReimbursed() {
        return this.paid;
    }
}
