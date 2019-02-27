package Helpers;

import javax.xml.bind.TypeConstraintException;
import java.lang.reflect.Array;

public class Binary {

    public static byte[] toBitArray(byte n) {
        byte[] ans = new byte[8];
        for(int val = 128, i = 0; val > 0; val /= 2, i++) {
            if(val <= n) {
                n -= val;
                ans[i] = 1;
            } else {
                ans[i] = 0;
            }
        }
        return ans;
    }

    public static byte toByte(byte[] arr) throws TypeConstraintException {
        byte ans = 0;
        if(arr.length > 8) {
            throw new TypeConstraintException("Array length is too big.");
        }
        byte val = (byte)(1 << ( arr.length - 1 ));

        for(int i = 0; i < arr.length; val /= 2, i++) {
            if(arr[i] == 1) {
                ans += val;
            }
        }
        return ans;
    }

}
