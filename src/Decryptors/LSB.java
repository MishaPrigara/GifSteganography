package Decryptors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LSB {

    public void Encrypt(File in, File out, String text) throws IOException {
        if(!in.isFile()) {
            throw new IOException("No input file");
        }
        if(!out.isFile()) {
            throw new IOException("No output file");
        }
        if(text == null) {
            throw new IOException("No input text");
        }

        byte[] bytes = new byte[(int)in.length()];
        InputStream is = new FileInputStream(in);
        is.read(bytes);
        is.close();

        for(int i = 0; i < Math.min(10, in.length()); i++) {
            System.out.println(bytes[i]);
        }
    }
}
