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
    boolean stop;
    Socket s;
    JTextPane textPane;
    DataInputStream din;
    DataOutputStream dout;

    public Server(int port, JTextPane textPane) throws IOException {
        stop = false;
        this.port = port;
        this.textPane = textPane;
        ss = new ServerSocket(port);
        s = ss.accept();

    }

    public void listen() throws IOException {
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
        while (!stop) {
            byte[] messageReceived = new byte[256];
            try {
                din.readFully(messageReceived);
                String message = translate(messageReceived, 20);
                appendS("Tu: " + message, Color.BLACK, false);
                //area.append("Tu: " + message + "\n");
            } catch (EOFException e) {
                appendS("Stopped listening", Color.RED, true);
                //area.append("Stopped listening " + "\n");
                stopServer();
                break;
            }

        }

    }

    public void send(String message) throws IOException {
        dout.writeUTF(message);
        dout.flush();

    }

    public void stopServer() throws IOException {
        System.out.println("gonna close the server");
        stop = true;

        din.close();
        dout.close();
        s.close();
        ss.close();
    }

    String translate(byte[] messageIn, int off) {
        String message = "";
        int size = messageIn[9];
        for (int c = off; c < off + size; c++) {
            message = message + (char) messageIn[c];
        }
        return message;
    }

    public void appendS(String s, Color color, boolean isBold) {
        try {
            SimpleAttributeSet keyWord = new SimpleAttributeSet();
            StyleConstants.setForeground(keyWord, color);
            StyleConstants.setBold(keyWord, isBold);
            textPane.getDocument().insertString(textPane.getDocument().getLength(), s + "\n", keyWord);
        } catch(BadLocationException exc) {
            exc.printStackTrace();
        }
    }

}
