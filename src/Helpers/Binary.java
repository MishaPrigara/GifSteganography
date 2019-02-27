package Helpers;

import javax.xml.bind.TypeConstraintException;
import java.lang.reflect.Array;

public class Binary {

    public static byte[] toBitArray(byte n) {
        byte[] ans = new byte[8];
        String s = String.format("%8s", Integer.toBinaryString(n & 0xFF)).replace(' ', '0');
        for(int i = 0; i < ans.length; i++) {
            if(s.charAt(i) == '1') {
                ans[i] = 1;
            } else {
                ans[i] = 0;
            }
        }
        return ans;
    }

    public static byte toByte(byte[] arr) throws TypeConstraintException {

        String s = "";

        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == 1) {
                s += '1';
            } else {
                s += '0';
            }
        }

        return Byte.parseByte(s, 2);
    }

}
