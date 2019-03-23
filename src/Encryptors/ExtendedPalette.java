package Encryptors;

import Helpers.Binary;
import Helpers.EncryptException;

import java.io.*;

public class ExtendedPalette {


    public void Encrypt (File in, File out, String text, byte key1) throws IOException, EncryptException {
        if (!in.isFile()) {
            throw new IOException("No input file");
        }
        if (!out.isFile()) {
            throw new IOException("No output file");
        }
        if (text == null) {
            throw new IOException("No input text");
        }
        if (key1 < 0) {
            throw new EncryptException("key1 is negative: " + key1);
        }

        byte[] bytes = new byte[(int)in.length()];
        InputStream is = new FileInputStream(in);
        is.read(bytes);
        is.close();

        if (in.length() < 6 || !(new String(bytes, 0, 6)).equals("GIF89a")) {
            throw new EncryptException("Input file has wrong GIF format");
        }

        byte[] b10 = Binary.toBitArray(bytes[10]);
        byte bsize = Binary.toByte(new byte[] {b10[0], b10[1], b10[2]});

        int palleteSize = (1 << (bsize + 1));

        int initalSize = palleteSize * 3;
        int added = 256 * 3  - initalSize;

        if(256 * 3 < text.length()) {
            throw new EncryptException("Text is too long!");
        }

        byte[] resBytes = new byte[(int)in.length() + added];

        int curByte = 13;

        for(int i = 0; i < 13; i++) {
            resBytes[i] = bytes[i];
        }

        for(int i = 0; i < initalSize; i++) {
            resBytes[curByte + i] = bytes[curByte + i];
        }

        curByte = 13 + 256 * 3;

        byte[] textBytes = text.getBytes();

        for(int i = 0; i < text.length(); i++) {
            resBytes[curByte - text.length() + i] = textBytes[i];
        }

        for(int i = 0; i < in.length() - (13 + initalSize); i++) {
            resBytes[curByte + i] = bytes[13 + initalSize + i];
        }

        OutputStream os = new FileOutputStream(out);
        os.write(resBytes);
        os.close();
    }
}
