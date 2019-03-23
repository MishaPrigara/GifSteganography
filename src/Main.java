import Encryptors.ExtendedPalette;
import Encryptors.LSB;
import Helpers.DecryptException;
import Helpers.EncryptException;

import java.io.File;
import java.io.IOException;

public class Main {

    private static LSB lsb = new LSB();
    private static ExtendedPalette extendedPalette = new ExtendedPalette();
    public static void main(String[] args) throws IOException {
        tryEP("aloprivet");
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
    }

}
