package com.unionpay.wallet.udf;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DESCryptUtil {
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static byte[] DEFAULT_KEY;
    static {
        DEFAULT_KEY = hexToBytes("2c40a4debe4ffadd2cf2c5f17ca6e20c2c40a4debe4ffadd");
    }

    public DESCryptUtil() {
    }

    public static String encrypt3DES(String value) throws UnsupportedEncodingException {
        if (null != value && !"".equals(value)) {
            byte[] valueByte = null;
            valueByte = value.getBytes("UTF-8");
            byte[] sl = encrypt3DES(valueByte, DEFAULT_KEY);
            String result = bytesToHex(sl);
            return result;
        } else {
            return "";
        }
    }

    public static String encrypt3DES(String value, String key) throws UnsupportedEncodingException {
        if (null != value && !"".equals(value)) {
            byte[] valueByte = null;
            valueByte = value.getBytes("UTF-8");
            byte[] sl = encrypt3DES(valueByte, hexToBytes(key));
            String result = bytesToHex(sl);
            return result;
        } else {
            return "";
        }
    }

    public static byte[] encrypt3DES(byte[] input, byte[] key) {
        byte[] ret = null;

        try {
            Cipher c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c.init(1, new SecretKeySpec(key, "DESede"));
            ret = c.doFinal(input);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return ret;
    }

    public static String decrypt3DES(String value) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] valueByte = hexToBytes(value);
        byte[] sl = decrypt3DES(valueByte, DEFAULT_KEY);
        return new String(sl, Charset.forName("UTF-8"));
    }

    public static String decrypt3DES(String value, String key) {
        if (null != value && !"".equals(value) && value.length() >= 32) {
            try {
                byte[] valueByte = hexToBytes(value);
                byte[] sl = decrypt3DES(valueByte, hexToBytes(key));
                return new String(sl, Charset.forName("UTF-8"));
            } catch (Exception e) {
                return null;
            }
        } else {
            return "";
        }
    }

    public static byte[] decrypt3DES(byte[] input, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] ret = null;
        Cipher c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        c.init(2, new SecretKeySpec(key, "DESede"));
        ret = c.doFinal(input);
        return ret;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 255;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 15];
        }

        return new String(hexChars);
    }

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }
}
