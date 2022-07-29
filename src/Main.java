import bankPack.Bank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Main {

    public static void setSerializedBank(Bank bank) {
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    "BankData.txt")));
            oos.writeObject(bank);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Bank getSerializedBank() {
        ObjectInputStream ois;
        Bank bank = null;

        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("BankData.txt")));
            bank = (Bank)ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Creation du fichier de sauvegarde...Ok");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bank;
    }

    public static void main(String[] args) {
        BankPan bp = new BankPan(getSerializedBank());

        JFrame frame = new JFrame("Bank");
        frame.setContentPane(bp.getContent());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("Sauvegarde...");
                setSerializedBank(bp.getBank());
                System.out.println("Ok");
            }
        });
    }
}