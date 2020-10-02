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
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author alex1
 */
public class Ciphero {

    public static byte[] decipher(String key, byte[] packet) throws InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidAlgorithmParameterException {
        byte[] de = new byte[256];

        byte[] realKey = getKeyInBytes(key);
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
        byte[] realKey = getKeyInBytes(key);
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
        for (int c = 0; c < 16; c+= 2) {
            String temp = "" + charA[c]+ charA[c+1];
            System.out.println(temp);
            int resulto = Integer.parseInt(temp, 16);
            if(resulto > 127){
                result[a] = (byte) ((byte) (resulto - 128)*-1);
            }else{
                result[a] = (byte) resulto;
            }
            System.out.println(result[a]);
            a++;
        }
        System.out.println("size "+a);
        return result;
    }
}