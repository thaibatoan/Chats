package chats;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Signin extends javax.swing.JFrame {

    private Timer timer;
    private boolean flag;
    private boolean done;
    private String username, password, serverIP = "10.28.225.88";
    private int Port = 1996;
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;

    public Signin() {
        this.done = false;
        this.flag = false;
        initComponents();
        initTextfield();
        MainPanel.setColors(new Color[]{new Color(200, 220, 220, 127), new Color(200, 220, 220, 127)});
        roundPanel.setFrac(new float[]{0f, 0.5f, 1f});
        roundPanel.setColors(new Color[]{new Color(200, 220, 220, 127), new Color(200, 220, 220, 127), new Color(200, 220, 220, 127)});
        roundPanel.setImageBackground(newRoundIcon("/image/admin.jpg", roundPanel.getHeight(), 0.5f).getImage());
        MainPanel.setImageBackground(newIcon("/image/background/white-wallpaper.png", MainPanel.getHeight(), MainPanel.getWidth()).getImage());
        Signin.setImageBackground(newRoundIcon("/image/play.png", Signin.getHeight(), .8f).getImage());
        sideButton.setColors(new Color[]{new Color(255, 255, 255, 0), new Color(255, 255, 255, 64), new Color(255, 255, 255, 0)});
        sideButton.setFrac(new float[]{0f, 0.5f, 1f});
        sideButton.setImageBackground(newIcon("/image/next_white.png", sideButton.getWidth(), sideButton.getWidth()).getImage());
        roundPanel.remove(rbtn1);
        timer = new Timer(30, (ActionEvent ae) -> {
            animation();
        });
    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;
            String stream, success = "Success", fail = "Fail";

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");
                    if (data[1].equals(success)) {
                        JOptionPane.showMessageDialog(null, "Login Success");
                        Chat_Client client = new Chat_Client();
                        client.setUsername(data[0]);
                        client.setLink("");
                        client.Connect();
                        client.setVisible(true);
                        sock.close();
                        dispose();
                    } else if (data[1].equals(fail)) {
                        JOptionPane.showMessageDialog(null, "Login Fail");
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Fail");
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    private void animation() {
        if (!flag) {
            sideButton.setAngle(sideButton.getAngle() - 20);
            if (newJPanel1.getX() - 50 > 0) {
                signin.setLocation(signin.getX() - 50, signin.getY());
                newJPanel1.setLocation(newJPanel1.getX() - 50, newJPanel1.getY());
            } else {
                signin.setLocation(-418, signin.getY());
                newJPanel1.setLocation(0, newJPanel1.getY());
                flag = true;
                timer.stop();
            }
            if (sideButton.getX() - 50 > 0) {
                sideButton.setLocation(sideButton.getX() - 50, sideButton.getY());
            } else {
                sideButton.setLocation(0, sideButton.getY());
            }
            repaint();
        } else {
            sideButton.setAngle(sideButton.getAngle() - 20);
            if (signin.getX() + 50 < 0) {
                signin.setLocation(signin.getX() + 50, signin.getY());
                newJPanel1.setLocation(newJPanel1.getX() + 50, newJPanel1.getY());
            } else {
                signin.setLocation(0, signin.getY());
                newJPanel1.setLocation(418, newJPanel1.getY());
                flag = false;
                timer.stop();
            }
            if (sideButton.getX() + 50 < 370) {
                sideButton.setLocation(sideButton.getX() + 50, sideButton.getY());
            } else {
                sideButton.setLocation(370, sideButton.getY());
            }
            repaint();
        }
    }

    private void initTextfield() {
        txt_Username.setIcon(newIcon("/image/user.png", 20, 20));
        txt_Password.setIcon(newIcon("/image/Password.png", 20, 20));
        if (txt_Username.getText().isEmpty()) {
            txt_Username.setText("Username");
            Font font = txt_Username.getFont();
            txt_Username.setFont(font.deriveFont(Font.ITALIC));
            txt_Username.setForeground(Color.GRAY);
        } else if (txt_Username.getText().equals("Username")) {
            txt_Username.setText("");
            Font font = txt_Username.getFont();
            txt_Username.setFont(font.deriveFont(Font.PLAIN));
            txt_Username.setForeground(Color.BLACK);
        }
        if (txt_Password.getPassword().length == 0) {
            txt_Password.setText("Password");
            Font font = txt_Password.getFont();
            txt_Password.setFont(font.deriveFont(Font.ITALIC));
            txt_Password.setForeground(Color.GRAY);
            txt_Password.setEchoChar((char) 0);
        } else if (new String(txt_Password.getPassword()).equals("Password")) {
            txt_Password.setText("Password");
            Font font = txt_Password.getFont();
            txt_Password.setFont(font.deriveFont(Font.PLAIN));
            txt_Password.setForeground(Color.BLACK);
            txt_Password.setEchoChar('\u2022');
        }
        roundPanel.requestFocusInWindow();
    }

    private ImageIcon newIcon(String path, int height, int width) {
        URL resource = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage();
        Image newimg = img;
        if (height != 0 && width != 0) {
            newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        }
        return new ImageIcon(newimg);
    }

    private ImageIcon newRoundIcon(String path, int radius, float alpha) {
        URL resource = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(radius, radius, java.awt.Image.SCALE_SMOOTH);
        Image b = new ImageIcon(newimg).getImage();
        BufferedImage a = imageToBufferedImage(newimg, radius, radius);
        a = makeRoundedCorner(a, radius, alpha);
        return new ImageIcon(a);
    }

    public BufferedImage imageToBufferedImage(Image im, int w, int h) {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    public BufferedImage makeRoundedCorner(BufferedImage image, int radius, float alpha) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, alpha));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Float(0, 0, radius, radius));

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    @SuppressWarnings("deprecation")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new chats.customui.RoundPanel();
        sideButton = new chats.customui.RoundButton();
        signin = new javax.swing.JPanel();
        roundPanel = new chats.customui.RoundPanel();
        Signin = new chats.customui.RoundButton();
        txt_Username = new chats.customui.RoundJTextField();
        txt_Password = new chats.customui.RoundJPasswordField();
        rbtn1 = new chats.customui.RoundButton();
        newJPanel1 = new chats.panels.NewJPanel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(418, 400));
        setMinimumSize(new java.awt.Dimension(418, 400));
        setPreferredSize(new java.awt.Dimension(418, 400));
        setResizable(false);

        MainPanel.setMaximumSize(new java.awt.Dimension(418, 330));
        MainPanel.setMinimumSize(new java.awt.Dimension(418, 330));
        MainPanel.setOpaque(false);
        MainPanel.setShape(chats.customui.ShapeType.Rectangle);
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideButton.setBorderPainted(false);
        sideButton.setContentAreaFilled(false);
        sideButton.setFocusPainted(false);
        sideButton.setShape(chats.customui.ShapeType.Rectangle);
        sideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sideButtonActionPerformed(evt);
            }
        });
        MainPanel.add(sideButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 40, 396));

        signin.setOpaque(false);

        roundPanel.setOpaque(false);
        roundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Signin.setBorder(null);
        Signin.setAlignmentX(0.5F);
        Signin.setBorderPainted(false);
        Signin.setContentAreaFilled(false);
        Signin.setNextFocusableComponent(txt_Username);
        Signin.setShape(chats.customui.ShapeType.Ellipse);
        Signin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SigninMouseReleased(evt);
            }
        });
        roundPanel.add(Signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 103, 45, 45));

        txt_Username.setCurve(30);
        txt_Username.setNextFocusableComponent(txt_Password);
        txt_Username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_UsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_UsernameFocusLost(evt);
            }
        });
        roundPanel.add(txt_Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 92, 170, 30));

        txt_Password.setCurve(30);
        txt_Password.setNextFocusableComponent(Signin);
        txt_Password.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PasswordFocusLost(evt);
            }
        });
        txt_Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_PasswordKeyPressed(evt);
            }
        });
        roundPanel.add(txt_Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 128, 170, 30));

        rbtn1.setBorder(null);
        rbtn1.setText("btn");
        rbtn1.setContentAreaFilled(false);
        rbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn1ActionPerformed(evt);
            }
        });
        roundPanel.add(rbtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 170, 100, 24));

        javax.swing.GroupLayout signinLayout = new javax.swing.GroupLayout(signin);
        signin.setLayout(signinLayout);
        signinLayout.setHorizontalGroup(
            signinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signinLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(roundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(488, 488, 488))
        );
        signinLayout.setVerticalGroup(
            signinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signinLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(roundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        MainPanel.add(signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        MainPanel.add(newJPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(418, 0, -1, -1));

        jPanel1.setPreferredSize(new java.awt.Dimension(418, 396));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );

        MainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(418, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn1ActionPerformed
        DangNhap();
    }//GEN-LAST:event_rbtn1ActionPerformed

    private void txt_PasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PasswordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            username = txt_Username.getText();
            password = txt_Password.getText();
            try {
                sock = new Socket(serverIP, Port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":" + password + ":Connect"); // Displays to everyone that user connected.
                writer.flush(); // flushes the buffer
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Cannot connect to Server. Please check your Internet connection!");
            }
            ListenThread();
        }
    }//GEN-LAST:event_txt_PasswordKeyPressed

    private void txt_PasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PasswordFocusLost
        if (txt_Password.getPassword().length == 0) {
            txt_Password.setText("Password");
            Font font = txt_Password.getFont();
            txt_Password.setFont(font.deriveFont(Font.ITALIC));
            txt_Password.setForeground(Color.GRAY);
            txt_Password.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_txt_PasswordFocusLost

    private void txt_PasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PasswordFocusGained
        if (new String(txt_Password.getPassword()).equals("Password")) {
            txt_Password.setText("");
            Font font = txt_Password.getFont();
            txt_Password.setFont(font.deriveFont(Font.PLAIN));
            txt_Password.setForeground(Color.GRAY);
            txt_Password.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_txt_PasswordFocusGained

    private void txt_UsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_UsernameFocusLost
        if (txt_Username.getText().isEmpty()) {
            txt_Username.setText("Username");
            Font font = txt_Username.getFont();
            txt_Username.setFont(font.deriveFont(Font.ITALIC));
            txt_Username.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txt_UsernameFocusLost

    private void txt_UsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_UsernameFocusGained
        if (txt_Username.getText().equals("Username")) {
            txt_Username.setText("");
            Font font = txt_Username.getFont();
            txt_Username.setFont(font.deriveFont(Font.PLAIN));
            txt_Username.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txt_UsernameFocusGained

    private void SigninMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SigninMouseReleased
        username = txt_Username.getText();
        password = txt_Password.getText();
        try {
            sock = new Socket(serverIP, Port);
            InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamreader);
            writer = new PrintWriter(sock.getOutputStream());
            writer.println(username + ":" + password + ":Connect"); // Displays to everyone that user connected.
            writer.flush(); // flushes the buffer
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Cannot connect to Server. Please check your Internet connection!");
        }
        ListenThread();
    }//GEN-LAST:event_SigninMouseReleased

    private void sideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sideButtonActionPerformed
        timer.start();
    }//GEN-LAST:event_sideButtonActionPerformed

    private void DangNhap() {
        try {
            File f = new File("E:/data.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            @SuppressWarnings("UnusedAssignment")
            String line = "";
            String[] arrline;
            Boolean check = false;
            while ((line = br.readLine()) != null) {
                arrline = line.split("/");
                char[] pass = txt_Password.getPassword();
                String password = new String(pass);
                if (password.equals(arrline[1]) && txt_Username.getText().equals(arrline[0])) {
                    check = true;
                    break;
                }
            }
            if (check) {
                JOptionPane.showMessageDialog(null, "Dang nhap thanh cong");
                this.dispose();
                Chat_Client clt = new Chat_Client();
                clt.setUsername(txt_Username.getText());
                clt.setLink("");
                clt.Connect();
                clt.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Sai tai khoan hoac mat khau");
            }
            br.close();
            fr.close();
        } catch (HeadlessException | IOException e) {
            JOptionPane.showMessageDialog(null, "Dang nhap that bai");
        }
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Signin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Signin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private chats.customui.RoundPanel MainPanel;
    private chats.customui.RoundButton Signin;
    private javax.swing.JPanel jPanel1;
    private chats.panels.NewJPanel newJPanel1;
    private chats.customui.RoundButton rbtn1;
    private chats.customui.RoundPanel roundPanel;
    private chats.customui.RoundButton sideButton;
    private javax.swing.JPanel signin;
    private chats.customui.RoundJPasswordField txt_Password;
    private chats.customui.RoundJTextField txt_Username;
    // End of variables declaration//GEN-END:variables
}
