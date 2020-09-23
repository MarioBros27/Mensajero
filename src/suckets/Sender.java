/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author andres
 */
public class Sender {

//    String ip;
//    int port;
    Socket s;
    DataOutputStream dout;


    public Sender(String ip, int port) throws IOException {
//        this.ip = ip;
//        this.port = port;
        s = new Socket(ip, port);
        dout = new DataOutputStream(s.getOutputStream());

    }

    public void send(String message) throws IOException {
//        dout.writeUTF(message);
        byte[] arr = getByteArray(message);
        dout.write(arr,0,256);
        dout.flush();
    }

    public void stopSender() throws IOException {
        s.close();
        dout.close();
    }
    private byte[] getByteArray(String message){
        char[] msg = message.toCharArray();
        byte[] arr = new byte[256];
        //ASCP
        arr[0]= 65;
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
        arr[11] = 1;
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
        for(int c = 20; c< 20 + msg.length; c++){
            arr[c] = (byte) msg[c - 20];
        }
        
        return arr;
    }
}
