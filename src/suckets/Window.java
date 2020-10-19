/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suckets;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author andres
 */
public class Window extends javax.swing.JFrame {

    /**
     * Creates new form Window
     */
    Server server;
    Thread thread;

    public Window() {
        initComponents();
        try {
            printIPs();
        } catch (SocketException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void printIPs() throws SocketException{
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets)) {
            displayInterfaceInformation(netint);
        }
    }
    void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
//        UIUtil.appendS(tx2,"Display name: "+ netint.getDisplayName(), Color.WHITE,false);
//        UIUtil.appendS(tx2,"Name: " +netint.getName(), Color.WHITE,false);
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            String add = inetAddress.toString();
            char[] arr = add.toCharArray();
            if(arr[1]=='1'&&arr[2]=='7'&&arr[3]=='2'){
                System.out.println("I'm here perro");
                UIUtil.appendS(textPane,"Mi IP: "+add,Color.PINK,false);
            }
            
        }

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectBtn = new javax.swing.JButton();
        messageTxt = new javax.swing.JTextField();
        sendBtn = new javax.swing.JButton();
        ipTuTxt = new javax.swing.JTextField();
        listenBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(520, 400));
        setResizable(false);

        connectBtn.setText("Connect");
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });

        messageTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageTxtKeyPressed(evt);
            }
        });

        sendBtn.setText("Send");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        ipTuTxt.setText("127.0.0.1");
        ipTuTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipTuTxtActionPerformed(evt);
            }
        });

        listenBtn.setBackground(new java.awt.Color(0, 0, 0));
        listenBtn.setText("Listen");
        listenBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listenBtnActionPerformed(evt);
            }
        });

        textPane.setEditable(false);
        textPane.setBackground(new java.awt.Color(0, 0, 0));
        textPane.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(textPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(messageTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendBtn)
                    .addComponent(listenBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ipTuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(listenBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ipTuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBtnActionPerformed
        // TODO add your handling code here:

        if (connectBtn.getText() == "Connect") {
            if (thread != null) {
                thread.stop();
            }
            String destIP = ipTuTxt.getText();

            thread = new Thread("server thread") {
                public void run() {

                    try {
                        server = new Server(destIP, textPane);
                        server.connect();
                    } catch (IOException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                        appendS("Connection IOException, dammit", Color.RED, true);
                    }

                }
            };
            thread.start();

            appendS("Connected to: " + ipTuTxt.getText(), Color.GREEN, true);
            connectBtn.setText("Disconnect");
            sendBtn.setEnabled(true);
            messageTxt.setEnabled(true);

        } else {
            try {
                thread.stop();
                appendS("Stopped connection", Color.GREEN, true);
                connectBtn.setText("Connect");
                server.stopConnection();
                sendBtn.setEnabled(false);
                messageTxt.setEnabled(false);

            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_connectBtnActionPerformed

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        if (messageTxt.getText().length() <= 236 && messageTxt.getText().length() > 0) {
            try {
                String message = messageTxt.getText();
                appendS("Yo: " + message, Color.WHITE, false);
                messageTxt.setText("");
                server.sendMessage(message,(byte)1,true);
            } catch (IOException ex) {
                appendS("Failed to send the message.", Color.RED, true);
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            appendS("Make sure the message is between 1 to 236 characters long.", Color.RED, true);
        }


    }//GEN-LAST:event_sendBtnActionPerformed

    private void ipTuTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipTuTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipTuTxtActionPerformed
    public void listen(){
         
        if (thread != null) {
            thread.stop();
            appendS("Restarting server...", Color.GREEN, true);
        }
        thread = new Thread("server thread") {
            public void run() {

                try {
                    server = new Server(textPane);
                    server.listenForConnection();
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    appendS("Servier IOException, junior disconnecte", Color.RED, true);
                }

            }
        };
        thread.start();
    }
    private void messageTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            sendBtn.doClick();
        }
    }//GEN-LAST:event_messageTxtKeyPressed

    private void listenBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listenBtnActionPerformed
        // TODO add your handling code here:
        listen();

    }//GEN-LAST:event_listenBtnActionPerformed

    public void appendS(String s, Color color, boolean isBold) {
        try {
            SimpleAttributeSet keyWord = new SimpleAttributeSet();
            StyleConstants.setForeground(keyWord, color);
            StyleConstants.setBold(keyWord, isBold);
            textPane.getDocument().insertString(textPane.getDocument().getLength(), s + "\n", keyWord);
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectBtn;
    private javax.swing.JTextField ipTuTxt;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton listenBtn;
    private javax.swing.JTextField messageTxt;
    private javax.swing.JButton sendBtn;
    private javax.swing.JTextPane textPane;
    // End of variables declaration//GEN-END:variables
}
