/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.awt.Color;

/**
 *
 * @author andres
 */
public class Util {

    public static String translate(byte[] messageIn) {
        int off = 20;
        String message = "";
        int size = messageIn[9] & 255;
        System.out.println("Size:" + size);
        for (int c = off; c < off + size&& c<235; c++) {
            message = message + (char) messageIn[c];
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
            System.out.println("@getHellman->"+value+": "+returnS);
            return returnS;

    }

    public static byte[] getByteArray(String message, byte function) {
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
        for (int c = 20; c < 20 + msg.length; c++) {
            arr[c] = (byte) msg[c - 20];
        }

        return arr;
    }
}
