/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author andres
 */
public class Server {

    int port;
    ServerSocket ss;
    Socket regularSocket;
    boolean stop;
    Socket s;
    JTextPane textPane;
    DataInputStream din;
    DataOutputStream dout;

    String ip;

    public Server(int port, JTextPane textPane) throws IOException {
        stop = false;
        this.port = port;
        this.textPane = textPane;
        ss = new ServerSocket(port);
        s = ss.accept();

    }

    public Server(String ip, int port,JTextPane textPane) throws IOException {
        stop = false;
        this.port = port;
        this.ip = ip;
        this.textPane = textPane;
        regularSocket = new Socket(ip, port);
    }

    public void listen() throws IOException {
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
        while (!stop) {
            byte[] messageReceived = new byte[256];
            try {
                din.readFully(messageReceived);
                String message = Util.translate(messageReceived, 20);
                UIUtil.appendS(textPane,"Tu: " + message, Color.BLACK, false);
            } catch (EOFException e) {
                UIUtil.appendS(textPane,"Stopped listening", Color.RED, true);
                stopServer();
                break;
            }

        }

    }

    public void connect() throws IOException{
        din = new DataInputStream(regularSocket.getInputStream());
        dout = new DataOutputStream(regularSocket.getOutputStream());
        while (!stop) {
            byte[] messageReceived = new byte[256];
            try {
                din.readFully(messageReceived);
                String message = Util.translate(messageReceived, 20);
                UIUtil.appendS(textPane,"Tu: " + message, Color.BLACK, false);
            } catch (EOFException e) {
                UIUtil.appendS(textPane,"Connection interrupted", Color.RED, true);
                stopConnection();
                break;
            }

        }
    }
    public void send(String message) throws IOException {
        byte[] arr = Util.getByteArray(message);
        dout.write(arr, 0, 256);
        dout.flush();

    }

    public void stopServer() throws IOException {
        System.out.println("Server closed");
        stop = true;

        din.close();
        dout.close();
        s.close();
        ss.close();
    }

    public void stopConnection() throws IOException {
        System.out.println("Closed connection");
        stop = true;
        din.close();
        dout.flush();
        dout.close();
        regularSocket.close();
    }

   

}
