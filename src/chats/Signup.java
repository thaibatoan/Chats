package chats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class Signup extends javax.swing.JFrame {

    public Signup() {
        initComponents();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(4, 2));

        jLabel2.setText("Username");
        jPanel1.add(jLabel2);
        jPanel1.add(txt_Username);

        jLabel3.setText("Email");
        jPanel1.add(jLabel3);
        jPanel1.add(txt_Email);

        jLabel4.setText("Password");
        jPanel1.add(jLabel4);
        jPanel1.add(txt_Password);

        jLabel1.setText("Confirm");
        jPanel1.add(jLabel1);
        jPanel1.add(txt_Confirm);

        btn_Signup.setText("Sign up");
        btn_Signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SignupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(btn_Signup, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Signup, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SignupActionPerformed
        // TODO add your handling code here:
        DangKi();
    }//GEN-LAST:event_btn_SignupActionPerformed

    public void DangKi() {

        try {
            File f = new File("E:/data.txt");
            FileWriter fw = new FileWriter(f, true);
            BufferedWriter bw = new BufferedWriter(fw);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String lineW = "";
            String lineR = "";
            String[] arrlineR;
            Boolean check = false;

            while ((lineR = br.readLine()) != null) {
                arrlineR = lineR.split("/");
                if (txt_Username.getText().toString().equals(arrlineR[0])) {
                    check = true;
                    br.close();
                    fr.close();
                    break;
                }
            }
            if (check) {
                JOptionPane.showMessageDialog(null, "Tai khoan da ton tai");
            } else {
                char[] pass = txt_Password.getPassword();
                String password = new String(pass);
                char[] pass2 = txt_Confirm.getPassword();
                String password2 = new String(pass2);
                if (txt_Password.getText().equals(txt_Confirm.getText())) {
                    lineW = txt_Username.getText().toString() + "/" + txt_Password.getText();
                    bw.write(lineW);
                    bw.newLine();
                    bw.close();
                    fw.close();
                    JOptionPane.showMessageDialog(null, "Dang ki thanh cong");
                    this.hide();
                    Signin sn = new Signin();
                    sn.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Dang ki that bai");
                }

            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            JOptionPane.showMessageDialog(null, "Dang ki that bai");
        }

    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new Signup().setVisible(true);
        });
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
