import Encryptors.LSB;
import Helpers.EncryptException;

import java.io.File;
import java.io.IOException;

public class Main {

    static LSB lsb = new LSB();

    public static void main(String[] args) throws IOException, EncryptException {
        try {
            lsb.Encrypt(new File("/home/mishaprigara/Downloads/earth.gif"),
                    new File("/home/mishaprigara/Downloads/earth.gif"), "kek", (byte)0, 0);
        } catch (EncryptException err) {
            System.out.println("Encryption failed: " + err);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        }
        System.out.println("kek");
    }
}
