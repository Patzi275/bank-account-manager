import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import bankPack.*;
import bankPack.bankExceptions.BadLoanIndexException;


public class BankPan {

    private Bank bank;
    public enum Page {MAIN, CREATE_ACCOUNT, DEPOSIT, WITHDRAW, MAKE_LOAN, PAY_LOAN, SEARCH_ACCOUNT, SHOW_ACCOUNTS}

    private JPanel cardPan;
    private JPanel mainPan;
    private JButton ajouterUnCompteButton;
    private JButton effectuerDepotButton;
    private JButton effectuerRetraitButton;
    private JButton accorderPretButton;
    private JButton rembourserPretButton;
    private JButton quitterButton;
    private JButton rechercherCompteButton;
    private JButton afficherComptesButton;
    private JPanel addAccountPan;
    private JPanel makeADepositPan;
    private JPanel makeAWithDraw;
    private JPanel makeALoan;
    private JPanel payALoan;
    private JPanel searchAccount;
    private JPanel Options;
    private JTextField addNumeroTf;
    private JLabel numeroAlertLabel;
    private JLabel nameAlertTf;
    private JLabel soldeAlertTf;
    private JLabel interetAlertTf;
    private JButton addValiderButton;
    private JButton addAnnulerButton;
    private JTextField addNomTf;
    private JTextField addSoldeTf;
    private JTextField addInterestTf;
    private JTextField mdNumeroTf;
    private JButton mdAnnulerButton;
    private JButton mdValiderButton;
    private JLabel mdNumeroAlertLabel;
    private JLabel mdMontantAlertLabel;
    private JTextField mdMontantTf;
    private JTextField saNumeroTf;
    private JTextPane searchResultPan;
    private JButton saRetourplay4Button;
    private JRadioButton compteNormalRadioButton;
    private JRadioButton compteDEpargneRadioButton;
    private JButton aaRetourButton;
    private JButton saRetourButton;
    private JButton mdRetourButton;
    private JButton mwRetourButton;
    private JTextField mwNumeroTf;
    private JTextField mwMontantTf;
    private JButton mwAnnulerButton;
    private JButton mwValiderButton;
    private JLabel mwNumeroAlert;
    private JLabel mwMontantAlert;
    private JTextField mlNumeroTf;
    private JTextField mlSommeTf;
    private JTextField mlMensualiteTf;
    private JButton mlAnnuleButton;
    private JButton mlValideButton;
    private JLabel mlMensualiteAlert;
    private JLabel mlSommeAlert;
    private JLabel mlNumeroAlert;
    private JButton mlRetourButton;
    private JButton button1;
    private JTabbedPane tabbedPane1;
    private JButton plRetourButton;
    private JTextPane pretTextPane;
    private JTextField plNumeroPretTf;
    private JTextField plNumeroTf;
    private JLabel plNumeroAlert;
    private JLabel plNumeroPretAlert;
    private JButton plAnnulerButton;
    private JButton plValiderButton;
    private JCheckBox plPayAllLoansCheckBox;
    private JComboBox saSearchType;
    private JCheckBox regexCheckBox;

    public BankPan(Bank theBank) {
        if (theBank == null)
            this.bank = new Bank();
        else
            this.bank = theBank;
        //AddTestData();
        cardPan.setPreferredSize(new Dimension(450, 350));

        //Accueil
        ajouterUnCompteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetAddAccountForm();
                setPage(Page.CREATE_ACCOUNT);
            }
        });
        effectuerDepotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetMakeDepositForm();
                setPage(Page.DEPOSIT);
            }
        });
        effectuerRetraitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetMakeWithDrawForm();
                setPage(Page.WITHDRAW);
            }
        });
        accorderPretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetMakeLoanForm();
                setPage(Page.MAKE_LOAN);
            }
        });
        rembourserPretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetPayALoan();
                setPage(Page.PAY_LOAN);
            }
        });
        rechercherCompteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fillSearchTextPan(bank.getListBeginingBy("[a-zA-Z0-9]*", getSearchType(), true));
                setPage(Page.SEARCH_ACCOUNT);
            }
        });
        afficherComptesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPage(Page.SHOW_ACCOUNTS);
            }
        });
        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Sauvegarde...");
                Main.setSerializedBank(bank);
                System.out.println("Ok");
                System.exit(0);
            }
        });

        //Ajouter compte
        addValiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String numero = addNumeroTf.getText();
                String nom = addNomTf.getText();
                boolean retour = false;
                BankAccount newAccount = null;
                int solde = 0;
                double interet = 0;
                //Verificiation numero de banque
                if (bank.getAccount(numero) != null) {
                    numeroAlertLabel.setText("Ce numero de compte existe deja");
                    retour = true;
                } else {
                    numeroAlertLabel.setText("");
                }

                //Vérification nom
                if (nom.strip().isEmpty() || nom.isBlank()) {
                    nameAlertTf.setText("Veuillez entrer un nom valide.");
                    retour = true;
                } else {
                    nameAlertTf.setText("");
                }

                //Vérification solde
                try {
                    solde = Integer.parseInt(addSoldeTf.getText());
                    if (solde < 0) {
                        soldeAlertTf.setText("Le solde doit être un nombre positif"); retour = true;
                    } else {
                        soldeAlertTf.setText("");
                    }
                } catch (NumberFormatException e) {
                    soldeAlertTf.setText("Le solde doit être un nombre positif"); retour = true;
                }

                //Vérification interet
                try {
                    interet = Double.parseDouble(addInterestTf.getText());
                    if (interet < 0 || interet > 100) {
                        interetAlertTf.setText("L'interet doit être un nombre décimal compris entre 1 et 100"); retour = true;
                    } else {
                        interetAlertTf.setText("");
                    }
                } catch (NumberFormatException e) {
                    interetAlertTf.setText("L'interet doit être un nombre décimal compris entre 1 et 100"); retour = true;
                }

                if (!retour) {
                    if (compteNormalRadioButton.isSelected()) {
                        newAccount = new NormalAccount(nom, numero, solde, interet);
                    }
                    else if (compteDEpargneRadioButton.isSelected()) {
                        newAccount = new SparingAccount(nom, numero, solde, interet);
                    }
                    bank.addAccount(newAccount);

                    //Pop
                    JOptionPane jp = new JOptionPane();
                    jp.showMessageDialog(addAccountPan, "Compte enregistrée", "Information", JOptionPane.INFORMATION_MESSAGE);

                    //Reinitialisation
                    resetAddAccountForm();
                }
            }
        });
        compteNormalRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (compteNormalRadioButton.isSelected()) {
                    compteDEpargneRadioButton.setSelected(false);
                }
            }
        });
        compteDEpargneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (compteDEpargneRadioButton.isSelected())
                    compteNormalRadioButton.setSelected(false);
            }
        });
        addAnnulerButton.addActionListener(new goToMainListener());
        aaRetourButton.addActionListener(new goToMainListener());

        //Faire un pret
        mdRetourButton.addActionListener(new goToMainListener());
        mdAnnulerButton.addActionListener(new goToMainListener());
        mdValiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String numero = mdNumeroTf.getText();
                int montant = 0;
                boolean retour = false;

                if (bank.getAccount(numero) == null) {
                    mdNumeroAlertLabel.setText("Ce numero de compte n'existe pas");
                    retour = true;
                } else {
                    mdNumeroAlertLabel.setText("");
                }

                try {
                    montant = Integer.parseInt(mdMontantTf.getText());
                    if (montant < 0) {
                        mdMontantAlertLabel.setText("Le montant doit être un nombre positif"); retour = true;
                    } else {
                        mdMontantAlertLabel.setText("");
                    }
                } catch (NumberFormatException e) {
                    mdMontantAlertLabel.setText("Le montant doit être un nombre positif");
                    retour = true;
                }

                if (!retour) {
                    BankAccount ac = bank.getAccount(numero);
                    int lastSolde = ac.getSolde();
                    bank.operateADepository(numero, montant);

                    //Pop
                    JOptionPane jp = new JOptionPane();
                    jp.showMessageDialog(makeADepositPan, "Depôt enregistré\nSolde: " + lastSolde + "FCFA à " + ac.getSolde() + " FCFA." , "Information", JOptionPane.INFORMATION_MESSAGE);

                    //Reinitialisation
                    resetMakeDepositForm();
                }
            }
        });

        //Faire un retrait
        mwRetourButton.addActionListener(new goToMainListener());
        mwAnnulerButton.addActionListener(new goToMainListener());
        mwValiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String numero = mwNumeroTf.getText();
                int montant = 0;
                boolean retour = false;
                BankAccount account;

                account = bank.getAccount(numero);
                if (account == null) {
                    mwNumeroAlert.setText("Ce numero de compte n'existe pas");
                    retour = true;
                } else {
                    mwNumeroAlert.setText("");
                }


                try {
                    montant = Integer.parseInt(mwMontantTf.getText());

                    if (montant < 0) {
                        mwMontantAlert.setText("Le montant doit être un nombre positif");
                        retour = true;
                    } else if (account != null) {
                        if (account.getSolde() < montant) {
                            JOptionPane jp = new JOptionPane();
                            jp.showMessageDialog(makeAWithDraw, "Le solde est insuffisant: " + account.getSolde() + " FCFA.", "Information", JOptionPane.INFORMATION_MESSAGE);
                            retour = true;
                        }
                    } else {
                        mwMontantAlert.setText("");
                    }
                } catch (NumberFormatException e) {
                    mwMontantAlert.setText("Le montant doit être un nombre positif");
                    retour = true;
                }

                if (!retour) {
                    BankAccount ac = bank.getAccount(numero);
                    int lastSolde = ac.getSolde();
                    bank.operateAWithDraw(numero, montant);

                    //Pop
                    JOptionPane jp = new JOptionPane();
                    jp.showMessageDialog(makeAWithDraw, "Retrait reussit\nSolde: " + lastSolde + "FCFA à " + ac.getSolde() + " FCFA.", "Information", JOptionPane.INFORMATION_MESSAGE);

                    //Reinitialisation
                    resetMakeWithDrawForm();
                }
            }
        });

        //Faire un prêt
        mlRetourButton.addActionListener(new goToMainListener());
        mlAnnuleButton.addActionListener(new goToMainListener());
        mlValideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String numero = mlNumeroTf.getText();
                int montant = 0;
                int mensualite = 0;
                boolean retour = false;
                BankAccount account;

                account = bank.getAccount(numero);
                if (account == null) {
                    mlNumeroAlert.setText("Ce numero de compte n'existe pas");
                    retour = true;
                } else if (bank.isASparingAccount(bank.getAccountIndex(numero))) {
                    JOptionPane jp = new JOptionPane();
                    jp.showMessageDialog(makeALoan, "Prêt impossible. Ce compte est un compte d'épargne !", "Information", JOptionPane.INFORMATION_MESSAGE);
                    mlNumeroAlert.setText("Identifiant de compte incompatible.");
                    retour = true;
                } else {
                    mlNumeroAlert.setText("");
                }

                try {
                    montant = Integer.parseInt(mlSommeTf.getText());

                    if (montant < 0) {
                        mlSommeAlert.setText("Le montant doit être un nombre positif");
                        retour = true;
                    } else {
                        mlSommeAlert.setText("");
                    }
                } catch (NumberFormatException e) {
                    mlSommeAlert.setText("Le montant doit être un nombre positif");
                    retour = true;
                }

                try {
                    mensualite = Integer.parseInt(mlMensualiteTf.getText());

                    if (mensualite < 0) {
                        mlMensualiteAlert.setText("La mensualité doit être un nombre positif");
                        retour = true;
                    } else {
                        mlMensualiteAlert.setText("");
                    }
                } catch (NumberFormatException e) {
                    mlMensualiteAlert.setText("La mensualité doit être un nombre positif");
                    retour = true;
                }

                if (!retour) {
                    int lastSolde = account.getSolde();
                    bank.makeALoan(numero, montant, mensualite);

                    //Pop
                    JOptionPane jp = new JOptionPane();
                    jp.showMessageDialog(makeALoan, "Prêt de " + montant + " FCFA accordé\nSolde: " + lastSolde + "FCFA ---> " + account.getSolde() + " FCFA.", "Information", JOptionPane.INFORMATION_MESSAGE);

                    //Reinitialisation
                    resetMakeLoanForm();
                }
            }
        });

        //Payer pret
        plRetourButton.addActionListener(new goToMainListener());
        plAnnulerButton.addActionListener(new goToMainListener());
        plNumeroTf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                super.keyReleased(ke);
                pretTextPane.setText("");
                if (!(ke.getKeyChar() == 27 || ke.getKeyChar() == 65535)) {
                    String accountNumber = plNumeroTf.getText().strip();

                    if (!accountNumber.isBlank() && !accountNumber.isEmpty()){
                        BankAccount account = bank.getAccount(accountNumber);

                        if (account == null) {
                            plNumeroAlert.setText("Ce numero de compte n'existe pas.");
                        } else if (account instanceof SparingAccount) {
                            plNumeroAlert.setText("Ce compte est un compte d'épargne.");
                        } else {
                            plNumeroAlert.setText("");
                            fillTextLoanPan(accountNumber);
                        }
                    }
                    else {
                        plNumeroAlert.setText("");
                    }
                }
            }
        });
        plValiderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String numero = plNumeroTf.getText();
                int pretNumero = 0;
                boolean retour = false;

                BankAccount account = bank.getAccount(numero);
                if (!plPayAllLoansCheckBox.isSelected()) {
                    try {
                        pretNumero = Integer.parseInt(plNumeroPretTf.getText());
                        if (account == null || account instanceof SparingAccount) {
                            plNumeroPretAlert.setText("Veuillez préciser un numero de compte existant et valide.");
                            retour = true;
                        } else
                            plNumeroPretAlert.setText("");
                    } catch (NumberFormatException e) {
                        plNumeroPretAlert.setText("Nombre positif requis");
                        retour = true;
                    }
                }

                if (!retour) {
                    if (plPayAllLoansCheckBox.isSelected()) {
                        boolean paid = ((NormalAccount) account).payYourLoans();
                        JOptionPane jp = new JOptionPane();
                        jp.showMessageDialog(payALoan, paid ? "Tout les prêt ont été payer" : "Un nombre maximum de prêt a été payer", "Information", JOptionPane.INFORMATION_MESSAGE);
                        fillTextLoanPan((NormalAccount) account);
                    } else {
                        try {
                            boolean paid = bank.operateLoanPayment(numero, pretNumero);
                            JOptionPane jp = new JOptionPane();
                            jp.showMessageDialog(payALoan, paid ? "Le prêt a été payé." : "Le solde est insuffisant.", "Information", JOptionPane.INFORMATION_MESSAGE);
                            if (paid)
                                fillTextLoanPan((NormalAccount) account);
                            plNumeroPretTf.setText("");
                        } catch (BadLoanIndexException e) {
                            plNumeroPretAlert.setText("Ce numéro de prêt n'existe pas");
                        }
                    }
                }
            }

        });
        plPayAllLoansCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (plPayAllLoansCheckBox.isSelected())
                    plNumeroPretTf.setEnabled(false);
                else
                    plNumeroPretTf.setEnabled(true);
            }
        });

        //Rechercher un compte
        saRetourButton.addActionListener(new goToMainListener());
        saNumeroTf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!(e.getKeyChar() == 27 || e.getKeyChar() == 65535)) {
                    String accountNumber = saNumeroTf.getText().strip();
                    boolean isChaineEmpty = accountNumber.isBlank() || accountNumber.isEmpty();
                    boolean isListAccountBlank = bank.getAccounts().isEmpty();

                    if ((!isChaineEmpty) || !isListAccountBlank){
                        ArrayList<BankAccount> listeComptes = bank.getListBeginingBy(accountNumber, getSearchType(), false);
                        fillSearchTextPan(listeComptes);
                    } else {
                        if (isListAccountBlank)
                            searchResultPan.setText("(Aucun compte enregistré)");
                        else if (isChaineEmpty)
                            fillSearchTextPan(bank.getListBeginingBy("[a-zA-Z0-9]*", getSearchType(), true));
                    }
                }
            }
        });
        saRetourButton.addActionListener(new goToMainListener());
    }

    private void AddTestData() {
        NormalAccount myAccount = new NormalAccount("Samuel", "921", 100, 3.4);
        myAccount.addALoan(new Loan(300, 50, myAccount));
        myAccount.addALoan(new Loan(2300, 100, myAccount));
        myAccount.addALoan(new Loan(6050, 100, myAccount));
        myAccount.addALoan(new Loan(400, 25, myAccount));

        bank.addAccount(new SparingAccount("Patrick", "3030", 3000, 4.5));
        bank.addAccount(myAccount);
        bank.addAccount(new NormalAccount("Diane", "111", 500, 3.4));
        bank.addAccount(new SparingAccount("Tony", "8J3", 1500, 3.8));
        bank.addAccount(new NormalAccount("Rita", "5R49", 0, 3.7));
        bank.addAccount(new NormalAccount("Poro", "7EF4", 90, 4.1));
        bank.addAccount(new SparingAccount("Vanupied", "7978", 480, 5.1));
        bank.addAccount(new SparingAccount("Jimy", "21", 900, 4.3));
        bank.addAccount(new NormalAccount("Flash", "23", 390, 3.9));

    }

    class goToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setPage(Page.MAIN);
        }
    }

    public JPanel getContent() {
        return cardPan;
    }

    public Bank getBank() {
        return bank;
    }

    private void setPage(Page p) {
        CardLayout cl = (CardLayout) cardPan.getLayout();
        cl.first(cardPan);
        for (int i = 0; i < p.ordinal(); i++) {
            cl.next(cardPan);
        }
    }

    private void fillTextLoanPan(String number) {
        BankAccount ac = bank.getAccount(number);
        fillTextLoanPan((NormalAccount) ac);
    }

    private void fillTextLoanPan(NormalAccount ac) {
        StringBuilder str = new StringBuilder();
        int idx = 0;
        pretTextPane.setText("");
        for (Loan l : ((NormalAccount)ac).getLoans()) {
            str.append(idx++).append(' ').append(l.getAmount()).append(" ").append(l.isReimbursed() ? "PAID" : "NOT PAID").append('\n');
        }
        pretTextPane.setText(str.toString());
    }

    private void fillSearchTextPan(ArrayList<BankAccount> b_account) {
        StringBuilder finalResult = new StringBuilder();
        for (BankAccount account : b_account) {
            finalResult.append(account.getNumber()).append('\t')
                    .append(account.getOwner()).append('\t')
                    .append(account.getSolde()).append('\t')
                    .append(account instanceof NormalAccount ? "Compte Normal" : "Compte d'épargne")
                    .append('\n');
        }
        searchResultPan.setText(finalResult.toString());
    }

    private Bank.CTYPE getSearchType() {
        String str = this.saSearchType.getSelectedItem().toString();
        if (str == "Numero") {
            return Bank.CTYPE.NUMERO;
        } else if (str == "Nom") {
            return Bank.CTYPE.NOM;
        }
        return Bank.CTYPE.NUMERO;
    }
    private void resetAddAccountForm() {
        addNumeroTf.setText("");
        addNomTf.setText("");
        addSoldeTf.setText("");
        addInterestTf.setText("");
        numeroAlertLabel.setText("");
        nameAlertTf.setText("");
        soldeAlertTf.setText("");
        interetAlertTf.setText("");
        compteDEpargneRadioButton.setSelected(true);
        compteNormalRadioButton.setSelected(false);
    }

    private void resetMakeDepositForm() {
        mdMontantTf.setText("");
        mdMontantAlertLabel.setText("");
        mdNumeroTf.setText("");
        mdNumeroAlertLabel.setText("");
    }

    private void resetMakeWithDrawForm() {
        mwMontantTf.setText("");
        mwMontantAlert.setText("");
        mwNumeroAlert.setText("");
        mwNumeroTf.setText("");
    }

    private void resetMakeLoanForm() {
        mlNumeroTf.setText("");
        mlNumeroAlert.setText("");
        mlMensualiteTf.setText("");
        mlMensualiteAlert.setText("");
        mlSommeTf.setText("");
        mlSommeAlert.setText("");
    }

    private void resetPayALoan() {
        plNumeroTf.setText("");
        plNumeroAlert.setText("");
        plNumeroPretTf.setText("");
        plNumeroPretAlert.setText("");
        plPayAllLoansCheckBox.setSelected(false);
        pretTextPane.setText("");
    }
}
