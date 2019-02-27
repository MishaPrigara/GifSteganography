import Encryptors.LSB;

import java.io.File;
import java.io.IOException;

public class Main {

    static LSB lsb = new LSB();

    public static void main(String[] args) throws IOException {
        lsb.Encrypt(new File("/home/mishaprigara/Downloads/earth.gif"),
                new File("/home/mishaprigara/Downloads/earth.gif"), "kek");
        System.out.println("kek");
    }
}
