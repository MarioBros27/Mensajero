/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author alex1
 */
public class Ciphero {

    public static byte[] decipher(String key, byte[] packet) throws InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] de = new byte[256];

        byte[] realKey = decodeHexString(key);
        DESKeySpec dks = new DESKeySpec(realKey);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        Key secretKey = factory.generateSecret(dks);
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);
        de = cipher.doFinal(packet);
        return de;
    }

    public static byte[] encipher(String key, byte[] packet) throws InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] de = new byte[256];
        byte[] realKey;
        realKey = decodeHexString(key);
        DESKeySpec dks = new DESKeySpec(realKey);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        Key secretKey = factory.generateSecret(dks);
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);
        de = cipher.doFinal(packet);

        return de;
    }

    private static byte[] getKeyInBytes(String key) {
        System.out.println(key);
        char[] charA = key.toCharArray();
        byte[] result = new byte[8];
        int a = 0;
        for (int c = 0; c < 16; c += 2) {
            String temp = "" + charA[c] + charA[c + 1];
            System.out.println(temp);
            int resulto = Integer.parseInt(temp, 16);
            if (resulto > 127) {
                result[a] = (byte) ((byte) (resulto - 128));
            } else {
                result[a] = (byte) resulto;
            }
            System.out.println(result[a]);
            a++;
        }
        System.out.println("size " + a);
        return result;
    }
    //Codigo para convertir bajado de https://www.baeldung.com/java-byte-arrays-hex-strings

    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }
}