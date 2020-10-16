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
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

public class Server {

    final int port = 2020;
    String ip;
    ServerSocket ss;
    boolean stop;

    Socket regularSocket;
    Socket s;
    JTextPane textPane;
    DataInputStream din;
    DataOutputStream dout;

    String q = "2426697107";
    String a = "17123207";
//    BigInteger q;
//    BigInteger a;
    String myY;
    
//    BigInteger yourY;
    BigInteger myX;
    BigInteger key;

    public Server(JTextPane textPane) throws IOException {
        stop = false;
        this.textPane = textPane;
    }

    public Server(String ip, JTextPane textPane) throws IOException {
        stop = false;
        this.ip = ip;
        this.textPane = textPane;
        regularSocket = new Socket(ip, port);
    }

    public void listenForConnection() throws IOException {
        try {
            ss = new ServerSocket(port);
            while (true) {
                UIUtil.appendS(textPane, "Waiting for someone to connect...", Color.GREEN, true);
                s = ss.accept();
                din = new DataInputStream(s.getInputStream());
                if (readCredentials()) {
                    sendHandshakeBack();
                    dout = new DataOutputStream(s.getOutputStream());
                    UIUtil.appendS(textPane, "Connection encrypted with: " + s.getRemoteSocketAddress().toString(), Color.GREEN, true);
                    listen();
                } else {
                    UIUtil.appendS(textPane, "Couldn't connect: failure with credentials, read logs", Color.RED, true);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean readCredentials() {
        byte[] messageReceived = new byte[256];
        UIUtil.appendS(textPane, "Waiting for your Q,A,Y...", Color.YELLOW, true);
        try {
            din.readFully(messageReceived);
            String message = Util.translate(messageReceived);

            String qValue = Util.getHellman(message, 'q');
            String aValue = Util.getHellman(message, 'a');
            String yValue = Util.getHellman(message, 'y');
            if (qValue.equals(q) && aValue.equals(a)) {
                List<BigInteger> credentials = Ciphero.gimmemyKeys(new BigInteger(qValue), new BigInteger(aValue), new BigInteger(yValue));
                myY = credentials.get(0).toString();
                key = credentials.get(1);
                return true;

            } else {
                System.out.println("Q and A don't match: Q: " + qValue + "A: " + aValue);
                return false;
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void sendHandshakeBack() {
        String message = "q=" + q + "a=" + a + "y=" + myY;
        try {
            this.sendMessage(message, (byte) 3, true);
        } catch (IOException ex) {
            System.out.println("error at sendhansdhakeback");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listen() throws IOException {

        while (!stop) {
            byte[] messageReceived = new byte[256];
            try {
                din.readFully(messageReceived);
                String message;

                byte[] decrypted;
                try {
                    decrypted = Ciphero.decipher(key, messageReceived);
                    message = Util.translate(decrypted);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    UIUtil.appendS(textPane, "Error decrypting message\n", Color.RED, true);
                    continue;
                }

                UIUtil.appendS(textPane, "Tu: " + message, Color.WHITE, false);
            } catch (EOFException e) {
                UIUtil.appendS(textPane, "Stopped listening", Color.RED, true);
                UIUtil.appendS(textPane, "Junior disconnected", Color.RED, true);
                closeSocket();
                break;
            }

        }

    }

    public void connect() throws IOException {
        din = new DataInputStream(regularSocket.getInputStream());
        dout = new DataOutputStream(regularSocket.getOutputStream());
        sendHandshake();
        if (this.areCredentialsOK() == false) {

            UIUtil.appendS(textPane, "credentials that you sent don't match, read logs", Color.RED, true);
        }else{
            UIUtil.appendS(textPane, "Succesfully encrypted connection!", Color.GREEN, true);
        }

        while (!stop) {
            byte[] messageReceived = new byte[256];
            try {
                din.readFully(messageReceived);
                String message;

                byte[] decrypted;
                try {
                    decrypted = Ciphero.decipher(key, messageReceived);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    UIUtil.appendS(textPane, "Error decrypting message\n", Color.RED, true);
                    continue;
                }
                message = Util.translate(decrypted);

                UIUtil.appendS(textPane, "Tu: " + message, Color.WHITE, false);
            } catch (EOFException e) {
                UIUtil.appendS(textPane, "Juanita left, disconnect & connect again", Color.RED, true);
                stopConnection();
                break;
            }

        }
    }

    private void sendHandshake() {
        List <BigInteger> list = Ciphero.gimmemyXY(new BigInteger(q), new BigInteger(a));
        myX = list.get(0);
        myY = list.get(1).toString();
        String message = "q=" + q + "a=" + a + "y=" + myY;
        try {
            this.sendMessage(message, (byte) 2, false);
        } catch (IOException ex) {
            System.out.println("error at sendhansdhake");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean areCredentialsOK() {
        byte[] messageReceived = new byte[256];
        UIUtil.appendS(textPane, "Sent you mine waiting for yours..", Color.yellow, true);
        try {
            din.readFully(messageReceived);
            String message = Util.translate(messageReceived);

            String qValue = Util.getHellman(message, 'q');
            String aValue = Util.getHellman(message, 'a');
            String yValue = Util.getHellman(message, 'y');
            if (qValue.equals(q) && aValue.equals(a)) {
                BigInteger yB = new BigInteger(yValue);
                
                key = yB.modPow(myX, new BigInteger(q));
                return true;

            } else {
                System.out.println("Q and A don't match: Q: " + qValue + "A: " + aValue);
                return false;
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void sendMessage(String message, byte function, boolean encrypted) throws IOException {
        try {
            byte[] arr = Util.getByteArray(message, function);
            if (encrypted) {
                byte[] encryptedText = Ciphero.encipher(key, arr);
                dout.write(encryptedText, 0, 256);
            } else {
                dout.write(arr, 0, 256);;
            }
            dout.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void closeSocket() throws IOException {
        System.out.println("Server closed");

        din.close();
        dout.close();
        s.close();

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
