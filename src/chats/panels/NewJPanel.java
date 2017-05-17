package chats.panels;

import chats.Signin;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class NewJPanel extends javax.swing.JPanel {

    private String username, password, serverIP = "10.28.225.88";
    private int Port = 1996;
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;

    public NewJPanel() {
        initComponents();
    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;
            String stream, success = "OK", fail = "EXIST";

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");
                    if (data[1].equals(success)) {
                        JOptionPane.showMessageDialog(null, "Signup Success");
                        sock.close();
                        Signin signin = new Signin();
                        signin.setVisible(true);
                    } else if (data[1].equals(fail)) {
                        JOptionPane.showMessageDialog(null, "Account has already been created. Please signup again!");
                    }
                }
            } catch (HeadlessException | IOException ex) {
            }
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Password = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        txt_Confirm = new javax.swing.JPasswordField();
        btn_Signup = new javax.swing.JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(418, 396));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Username");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 4, -1, -1));

        txt_Username.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txt_Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 120, 20));

        jLabel3.setText("Email");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 54, -1, -1));

        txt_Email.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txt_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 120, 20));

        jLabel4.setText("Password");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 104, -1, -1));

        txt_Password.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txt_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 120, 20));

        jLabel1.setText("Confirm");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 154, 49, -1));

        txt_Confirm.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(txt_Confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 120, 20));

        btn_Signup.setText("Sign up");
        btn_Signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SignupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btn_Signup, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Signup)
                .addGap(161, 161, 161))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SignupActionPerformed
        // TODO add your handling code here:
        DangKi();
    }//GEN-LAST:event_btn_SignupActionPerformed

    public void DangKi() {

        if (txt_Username.getText() != null && txt_Password.getText() != null && txt_Email.getText() != null && txt_Confirm.getText() != null) {
            username = txt_Username.getText();
            if (txt_Password.getText().equals(txt_Confirm.getText())) {
                password = txt_Password.getText();
                try {
                    sock = new Socket(serverIP, Port);
                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ":" + password + ":Signup"); // Displays to everyone that user connected.
                    writer.flush(); // flushes the buffer
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Cannot connect to Server. Please check your Internet connection!");
                }
            }
        }
        ListenThread();   
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Signup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_Confirm;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JPasswordField txt_Password;
    private javax.swing.JTextField txt_Username;
    // End of variables declaration//GEN-END:variables
}
