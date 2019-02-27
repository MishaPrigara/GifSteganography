import Encryptors.LSB;
import Helpers.DecryptException;
import Helpers.EncryptException;

import java.io.File;
import java.io.IOException;

public class Main {

    static LSB lsb = new LSB();

    public static void main(String[] args) throws IOException {
        try {
            lsb.Encrypt(new File("/home/mishaprigara/Downloads/kerker.gif"),
                    new File("/home/mishaprigara/Downloads/kerker.gif"), "kek", (byte)1, 0);
        } catch (EncryptException err) {
            System.out.println("Encryption failed: " + err);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        }
        String ans = "sad";
        try {
            ans = lsb.Decrypt(new File("/home/mishaprigara/Downloads/kerker.gif"), (byte)1, 0);
        } catch (DecryptException err) {
            System.out.println("Decryption failed: " + err);
        } catch (IOException err) {
            System.out.println("Something's wrong with input: " + err);
        }

        System.out.println(ans);
    }
}
