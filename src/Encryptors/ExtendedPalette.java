package Encryptors;

import Helpers.Binary;
import Helpers.DecryptException;
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

        if(254 * 3 < text.length()) {
            throw new EncryptException("Text is too long!");
        }

        int found;

        for(found = 16; found < 13 + initalSize; found += 3) {
            if(bytes[13] == bytes[found] && bytes[14] == bytes[found + 1] && bytes[15] == bytes[found + 2]) {
                break;
            }
        }

        for(; found < 13 + initalSize; found++) {
            bytes[found] = 0;
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

        int f = text.length() % 3;
        if(f == 0) f = 3;
        f = 3 - f;

        for(int i = 1; i <= f; i++) {
            resBytes[curByte - text.length() - i] = 0;
        }

        for(int i = 0; i < 3; i++) {
            resBytes[curByte - text.length() - f - 3 + i] = bytes[13 + i];
        }

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

    public String Decrypt(File in, byte key1) throws IOException, DecryptException {
        if (!in.isFile()) {
            throw new IOException("No input file");
        }
        if (key1 < 0) {
            throw new DecryptException("key1 is negative: " + key1);
        }

        byte[] bytes = new byte[(int) in.length()];
        InputStream is = new FileInputStream(in);
        is.read(bytes);
        is.close();

        if (!(new String(bytes, 0, 6)).equals("GIF89a")) {
            throw new DecryptException("Input file has wrong GIF format");
        }

        byte[] b10 = Binary.toBitArray(bytes[10]);
        byte bsize = Binary.toByte(new byte[]{b10[0], b10[1], b10[2]});

        int palleteSize = (1 << (bsize + 1));
        int maxLength = palleteSize * 3;

        if(maxLength != 256 * 3) {
            throw new DecryptException("There's no EP encryption.");
        }

        int curByte = 16;
        boolean found = false;

        for(; curByte < 13 + maxLength; curByte += 3) {
            if(bytes[13] == bytes[curByte] && bytes[14] == bytes[curByte + 1] && bytes[15] == bytes[curByte + 2]) {
                found = true;
                break;
            }
        }

        if(!found) {
            throw new DecryptException("There's no EP encryption found.");
        }

        curByte += 3;

        String res = "";

        for(; curByte < 13 + maxLength; curByte++) {
            if(bytes[curByte] == 0)continue;
            res += (char)bytes[curByte];
        }

        return res;
    }
}
