package Encryptors;

import Helpers.Binary;
import Helpers.DecryptException;
import Helpers.EncryptException;
import com.sun.istack.internal.NotNull;


import java.io.*;
import java.util.Arrays;

public class LSB {

    private byte[] bytes;

    private void put (@NotNull byte[] curArray, int fpos) {
        for (int i = 0; i < curArray.length / 2; i++) {
            if (curArray[i * 2] == 1) {
                bytes[fpos + i] |= (1);
            } else {
                bytes[fpos + i] &= ~(1);
            }
            if (curArray[i * 2 + 1] == 1) {
                bytes[fpos + i] |= (2);
            } else {
                bytes[fpos + i] &= ~(2);
            }
        }
    }

    private byte[] get(int fpos) {
        byte[] ans = new byte[8];
        for (int i = 0; i < 4; i++) {
            ans[i * 2] = (byte)(bytes[fpos + i] & (1));
            if(ans[i * 2] != 0) ans[i * 2] = 1;
            ans[i * 2 + 1] = (byte)(bytes[fpos + i] & (2));
            if(ans[i * 2 + 1] != 0) ans[i * 2 + 1] = 1;
        }
        return ans;
    }

    public void Encrypt (File in, File out, String text, byte key1) throws IOException, EncryptException {
        if (!in.isFile()) {
            throw new IOException("No input file");
        }
        if (!out.isFile()) {
            out.createNewFile();
        }
        if (text == null) {
            throw new IOException("No input text");
        }
        if (key1 < 0) {
            throw new EncryptException("key1 is negative: " + key1);
        }

        bytes = new byte[(int)in.length()];
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

        if ((maxLength - 1) < text.length()) {
            throw new EncryptException("Text is too long!");
        }

        int curByte = 13;

        byte[] curArray = Binary.toBitArray(key1);

        put(curArray, curByte);

        curByte += curArray.length / 2;



        byte[] textBytes = text.getBytes();

        for (int i = 0; i < textBytes.length; i++) {
            curArray = Binary.toBitArray(textBytes[i]);
            put(curArray, curByte);
            curByte += curArray.length / 2;
        }

        maxLength *= 4;

        for (; curByte - 13 < maxLength; curByte++) {
            bytes[curByte] &= ~(1);
            bytes[curByte] &= ~(2);
        }

        OutputStream os = new FileOutputStream(out);
        os.write(bytes);
        os.close();
    }

    public String Decrypt(File in, byte key1) throws IOException, DecryptException {
        if (!in.isFile()) {
            throw new IOException("No input file");
        }
        if (key1 < 0) {
            throw new DecryptException("key1 is negative: " + key1);
        }

        bytes = new byte[(int)in.length()];
        InputStream is = new FileInputStream(in);
        is.read(bytes);
        is.close();

        if (!(new String(bytes, 0, 6)).equals("GIF89a")) {
            throw new DecryptException("Input file has wrong GIF format");
        }

        byte[] b10 = Binary.toBitArray(bytes[10]);
        byte bsize = Binary.toByte(new byte[] {b10[0], b10[1], b10[2]});

        int palleteSize = (1 << (bsize + 1));
        int maxLength = palleteSize * 3 / 4;

        int curByte = 13;

        byte[] fkey = get(curByte);

        curByte += 4;

        
        if (Binary.toByte(fkey) != key1) {
            throw new DecryptException("Invalid key1. Found " + key1 + ", but file has " + Binary.toByte(fkey));
        }
        byte[] text = new byte[maxLength - 1];

        int lastPositive = -1;
        int pos = 0;
        maxLength *= 4;

        for (; curByte - 9 < maxLength; curByte += 4) {
            fkey = get(curByte);
            if(Binary.toByte(fkey) != 0) {
                lastPositive = pos;
            }
            text[pos++] = Binary.toByte(fkey);
        }

        text = Arrays.copyOfRange(text, 0, lastPositive + 1);

        for (int i = 0; i < text.length; i++) {
            if(text[i] < 32) {
                throw new DecryptException("Message got invisible characters " + text[i]);
            }
        }

        return new String(text);
    }
}
