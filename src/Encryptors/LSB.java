package Encryptors;

import Helpers.Binary;
import Helpers.EncryptException;

import java.io.*;

public class LSB {

    public void Encrypt(File in, File out, String text, byte key1, long key2) throws IOException, EncryptException {
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

        if (in.length() < 6 || !(new String(bytes, 0, 6)).equals("GIF89a")) {
            throw new EncryptException("Input file has wrong GIF format");
        }

        byte[] b10 = Binary.toBitArray(bytes[10]);
        byte bsize = Binary.toByte(new byte[] {b10[0], b10[1], b10[2]});

        int palleteSize = (1 << (bsize + 1));
        int maxLength = palleteSize * 3 / 4;

        if(maxLength - 1 < text.length()) {
            throw new EncryptException("Text is too long!");
        }

        int curByte = 13;

        byte[] curArray = Binary.toBitArray(key1);
        for(int i = 0; i < curArray.length / 2; i++) {
            if(curArray[i * 2] == 1) {
                bytes[curByte] |= (1 << 7);
            } else {
                bytes[curByte] &= ~(1 << 7);
            }
            if(curArray[i * 2 + 1] == 1) {
                bytes[curByte] |= (1 << 6);
            } else {
                bytes[curByte] &= ~(1 << 6);
            }
            curByte++;
        }

        byte[] textBytes = text.getBytes();

        for(int i = 0; i < textBytes.length; i++) {
            curArray = Binary.toBitArray(textBytes[i]);
            for(int j = 0; j < curArray.length / 2; j++) {
                if(curArray[i * 2] == 1) {
                    bytes[curByte] |= (1 << 7);
                } else {
                    bytes[curByte] &= ~(1 << 7);
                }
                if(curArray[i * 2 + 1] == 1) {
                    bytes[curByte] |= (1 << 6);
                } else {
                    bytes[curByte] &= ~(1 << 6);
                }
                curByte++;
            }
        }

        maxLength *= 4;

        for(; 13 + curByte <= maxLength; curByte++) {
            bytes[curByte++] &= ~(1 << 7);
            bytes[curByte++] &= ~(1 << 7);
        }

        OutputStream os = new FileOutputStream(out);
        os.write(bytes);
        os.close();
    }
}
