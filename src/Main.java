import Encryptors.ExtendedPalette;
import Encryptors.LSB;
import Helpers.Binary;
import Helpers.DecryptException;
import Helpers.EncryptException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    private static LSB lsb = new LSB();
    private static ExtendedPalette extendedPalette = new ExtendedPalette();

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        App frame = new App();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void tryLSB(String text) {
        try {
            lsb.Encrypt(new File("/home/mishaprigara/Downloads/kerker.gif"),
                    new File("/home/mishaprigara/Downloads/kerker.gif"), text, (byte)1);
        } catch (EncryptException err) {
            System.out.println("Encryption failed: " + err);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        }
        text = "Failed";
        try {
            text = lsb.Decrypt(new File("/home/mishaprigara/Downloads/kerker.gif"), (byte)1);
        } catch (DecryptException err) {
            System.out.println("Decryption failed: " + err);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        }
        System.out.println(text);
    }

    private static void tryEP(String text) {
        try {
            extendedPalette.Encrypt(new File("/home/mishaprigara/Downloads/test.gif"),
                    new File("/home/mishaprigara/Downloads/test.gif"), text, (byte)1);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        } catch (EncryptException err) {
            System.out.println("Encryption failed: " + err);
        }

        text = "Failed";

        try {
            text = extendedPalette.Decrypt(new File("/home/mishaprigara/Downloads/test.gif"), (byte)1);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        } catch (DecryptException err) {
            System.out.println("Decryption failed: " + err);
        }

        System.out.println(text);
    }

}
