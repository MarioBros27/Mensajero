/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author andres
 */
public class Server {
    
    int port;
    ServerSocket ss;
    boolean stop;
    Socket s;
    JTextArea area;
    DataInputStream din;
    DataOutputStream dout;
    
    public Server(int port, JTextArea area) throws IOException{
        stop = false;
        this.port = port;
        this.area = area;
        ss = new ServerSocket(port);
        s = ss.accept();
        
    }
    public void listen() throws IOException{
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
        while(!stop){
            byte[] messageReceived = new byte[256];
            din.readFully(messageReceived);
            String message = translate(messageReceived,20);
            area.append("Tu: "+message+"\n");
        }
        
    }
    public void send(String message) throws IOException{
        dout.writeUTF(message);
        dout.flush();
        
    }
    public void stopServer() throws IOException{
        stop = true;
        din.close();
        dout.close();
        s.close();
        ss.close();
    }
    String translate(byte[] messageIn, int off){
        String message = "";
        int size = messageIn[9];
        for(int c = off; c<off +size; c++){
            message = message + (char) messageIn[c];
        }
        return message;
    }
   
}
