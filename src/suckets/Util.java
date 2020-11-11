/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;


/**
 *
 * @author andres
 */
public class Util {

    public static String translate(byte[] messageIn) {
        int off = 20;
        String message = "";
        int size = messageIn[9] & 255;
        int function = messageIn[11];
        System.out.println(Arrays.toString(messageIn));
        System.out.println("function:" +function);
//        System.out.println("Size Yessica:" + size);
        for (int c = off; c < off + size&& c<235; c++) {
            message = message + (char) messageIn[c];
        }
        if(function == 1){
            try {
                byte[] allegedMac = Arrays.copyOfRange(messageIn, 236, 256);
                
                System.out.println(Arrays.toString(allegedMac));
                byte[] wholeThing = Arrays.copyOfRange(messageIn, 0, 236);
                
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] mac = md.digest(wholeThing);
                System.out.println(Arrays.toString(mac));
                if(Arrays.equals(allegedMac, mac) == false && mac != null){
                    return "Hola Alberto error";
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        return message;
    }
   

    public static String getHellman(String message, char value) {
        String returnS = "";
        char[] arr = message.toCharArray();
        int index = 0;
        for (int c = 0; c < message.length(); c++) {
            if (arr[c] == value && arr[c + 1] == '=') {
                index = c + 2;
                break;
            }

        }
            for (int c = index; c < arr.length; c++) {
                if(arr[c] == ','){
                    break;
                }
                returnS = returnS + arr[c];
                System.out.println(c);
            }
//            System.out.println("@getHellman->"+value+": "+returnS);
            return returnS;

    }

    public static byte[] getByteArray(String message, byte function,JCheckBox box) {
        char[] msg = message.toCharArray();
        byte[] arr = new byte[256];
        //ASCP
        arr[0] = 65;
        arr[1] = 83;
        arr[2] = 67;
        arr[3] = 80;
        //Version
        arr[4] = 0;
        arr[5] = 0;
        arr[6] = 0;
        arr[7] = 0;
        arr[8] = 1;
        //Size
        arr[9] = (byte) msg.length;
//        System.out.println("Size: "+arr[9]);
//        System.out.println("funcion"+function);
        //Function
        arr[10] = 0;
        arr[11] = function;
        //State
        arr[12] = 0;
        arr[13] = 0;
        arr[14] = 0;
        arr[15] = 0;
        //id_session
        arr[16] = 0;
        arr[17] = 0;
        arr[18] = 0;
        arr[19] = 0;
        for (int c = 20; c < 20 + msg.length && c<236; c++) {
            arr[c] = (byte) msg[c - 20];
        }
        if(box.isSelected()){
            return arr;
        }
        if(function == 1){
            byte[] withoutMAC = Arrays.copyOfRange(arr, 0, 236);
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] mac = md.digest(withoutMAC);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(withoutMAC);
                outputStream.write(mac);
                return outputStream.toByteArray();
            } catch (Exception ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        return arr;
    }
}
