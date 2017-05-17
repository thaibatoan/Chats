/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chats;

import chats.customui.*;
import chats.panels.EmojiconPanel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

public class Chat_Client extends javax.swing.JFrame {

    private String username;
    private String serverIP;
    private int Port;
    private Socket sock;
    private String link;
    public BufferedReader reader;
    public PrintWriter writer;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> userList = new ArrayList();
    private Boolean isConnected = false;
    private Thread thread;
    private StringTokenizer Tokenizer;
    private int TotalUserCount;
    private DataInputStream datainputstream;
    private String UserRoom;
    private Color[] ColorMap;
    private Popup popup;
    private EmojiconPanel pan;
    private int PrivateWindowCount;
    @SuppressWarnings("ProtectedField")
    protected Private[] privatewindow;

    public Chat_Client() {
        this.privatewindow = new Private[30];
        this.Port = 5000;
        serverIP =  "10.28.225.88";
        ConfigLookAndFeel();
        initComponents();
        SetAvatar();
        textFieldInit();
        buttonInit();
        Resize();
    }

    private void SetAvatar() {
        try {
            Avatar.setIcon(newRoundIcon(getLink(), 50));
        } catch (NullPointerException ex) {
            Avatar.setIcon(newRoundIcon("/image/admin.jpg", 50));
        }
    }

    private void ConfigLookAndFeel() {
        try {
            String laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Chat_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buttonInit() {
        sendButton.setIcon(newIcon("/image/send-512.png", sendButton.getSize().height, sendButton.getSize().height));
        sendButton.setFocusPainted(false);
        emojiButton.setFocusPainted(false);
        attachButton.setFocusPainted(false);
        emojiButton.setIcon(newIcon("/image/emoji.png", sendButton.getSize().height, sendButton.getSize().height));
        attachButton.setIcon(newIcon("/image/attachment.png", sendButton.getSize().height, sendButton.getSize().height));
    }

    private void textFieldInit() {
        searchtextfield.setIcon(newIcon("/image/Search.png", 15, 15));
        Font font = searchtextfield.getFont();
        searchtextfield.setFont(font.deriveFont(Font.ITALIC));
        searchtextfield.setForeground(Color.gray);
        inputfield.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        inputfield.setFont(font.deriveFont(Font.ITALIC));
        inputfield.setForeground(Color.gray);
        inputfield.setText("Type message");
    }

    private int LineCount(JTextPane tp) {
        int totalCharacters = tp.getDocument().getLength();
        int lineCount = (totalCharacters == 0) ? 1 : 0;

        try {
            int offset = totalCharacters;
            while (offset > 0) {
                offset = Utilities.getRowStart(tp, offset) - 1;
                lineCount++;
            }
        } catch (BadLocationException e) {
            e.printStackTrace(System.out);
        }
        return lineCount;
    }

    private void Resize() {
//<editor-fold defaultstate="collapsed" desc="Resize Chat Bubbles">
        for (int i = 0; i < AllChat.getComponentCount(); i++) {
            JPanel jp = (JPanel) AllChat.getComponent(i);
            JViewport viewport;
            JTextPane txtPane;
            int count, height;
            if (jp.getComponent(0) instanceof LeftArrowBubble) {
                LeftArrowBubble lab = (LeftArrowBubble) jp.getComponent(0);
                viewport = ((JScrollPane) lab.getComponent(0)).getViewport();
                txtPane = (JTextPane) viewport.getView();
                count = LineCount(txtPane);
                height = 20 * count + 20 + 25;
                jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
                jp.setMinimumSize(new Dimension(0, height));
                jp.setPreferredSize(new Dimension(100, height));
            } else if (jp.getComponent(0) instanceof RightArrowBubble) {
                RightArrowBubble lab = (RightArrowBubble) jp.getComponent(0);
                viewport = ((JScrollPane) lab.getComponent(0)).getViewport();
                txtPane = (JTextPane) viewport.getView();
                count = LineCount(txtPane);
                height = 20 * count + 20 + 25;
                jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
                jp.setMinimumSize(new Dimension(0, height));
                jp.setPreferredSize(new Dimension(100, height));
            } else {
                JPanel lab = (JPanel) jp.getComponent(0);
                viewport = ((JScrollPane) lab.getComponent(0)).getViewport();
                txtPane = (JTextPane) viewport.getView();
                count = LineCount(txtPane);
                height = 20 * count;
                jp.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
                jp.setMinimumSize(new Dimension(0, height));
                jp.setPreferredSize(new Dimension(100, height));
            }
        }
//</editor-fold>
    }

    private void CreateTextMessage(String user, String message) {
        if (user.startsWith("Admin")) {
            JPanel jp = new JPanel();
            JPanel left = new JPanel();
            left.setMaximumSize(new java.awt.Dimension(333, 30000));
            left.setMinimumSize(new java.awt.Dimension(52, 38));
            left.setPreferredSize(new java.awt.Dimension(222, 38));

            JScrollPane jsp = new JScrollPane() {
                @Override
                protected void processMouseWheelEvent(MouseWheelEvent e) {
                    if (!isWheelScrollingEnabled()) {
                        if (getParent() != null) {
                            getParent().dispatchEvent(
                                    SwingUtilities.convertMouseEvent(this, e, getParent()));
                        }
                        return;
                    }
                    super.processMouseWheelEvent(e);
                }

            };
            jsp.setBackground(new java.awt.Color(255, 255, 255));
            jsp.setBorder(null);
            jsp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jsp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            jsp.setWheelScrollingEnabled(false);

            JTextPane jtp = new JTextPane();
            jtp.setEditable(false);
            jtp.setBackground(new Color(234, 236, 242));
            jtp.setBorder(null);
            jtp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jtp.setContentType("text/html");
            jtp.setText("<div align=\"center\"><b font size=\"4\" style=\"color:red\";font-family:tahoma;>" + message + "</p> </div>");
            jsp.setViewportView(jtp);

            javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
            left.setLayout(leftLayout);
            leftLayout.setHorizontalGroup(
                    leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftLayout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(jsp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGap(100, 100, 100))
            );
            leftLayout.setVerticalGroup(
                    leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftLayout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addGap(0, 0, 0))
            );

            javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
            jp.setLayout(jpLayout);
            jpLayout.setHorizontalGroup(
                    jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLayout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addComponent(left, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addGap(0, 0, 0))
            );
            jpLayout.setVerticalGroup(
                    jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(left, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
            );
            jp.setBackground(new Color(234, 236, 242));
            left.setBackground(new Color(234, 236, 242));
            AllChat.add(jp);
        } else if (user.equals(getUsername())) {
            JPanel jp = new JPanel();
            LeftArrowBubble left = new LeftArrowBubble();
            JScrollPane jsp = new JScrollPane() {

                @Override
                protected void processMouseWheelEvent(MouseWheelEvent e) {
                    if (!isWheelScrollingEnabled()) {
                        if (getParent() != null) {
                            getParent().dispatchEvent(
                                    SwingUtilities.convertMouseEvent(this, e, getParent()));
                        }
                        return;
                    }
                    super.processMouseWheelEvent(e);
                }

            };
            JTextPane jtp = new JTextPane();
            left.setMaximumSize(new java.awt.Dimension(333, 30000));
            left.setMinimumSize(new java.awt.Dimension(52, 38));
            left.setPreferredSize(new java.awt.Dimension(222, 38));

            jsp.setBackground(new java.awt.Color(133, 90, 168));
            jsp.setBorder(null);
            jsp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jsp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            jsp.setWheelScrollingEnabled(false);

            jtp.setEditable(false);
            jtp.setBackground(new java.awt.Color(133, 90, 168));
            jtp.setBorder(null);
            jtp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jtp.setContentType("text/html");
            jtp.setText("<font size=\"4\" style=\"color:white\";font-family:tahoma;>" + message + "</p>");
            jsp.setViewportView(jtp);

            JLabel jl = new JLabel();
            jl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            jl.setForeground(Color.white);
            jl.setText(user);

            javax.swing.GroupLayout leftLayout = new javax.swing.GroupLayout(left);
            left.setLayout(leftLayout);
            leftLayout.setHorizontalGroup(
                    leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftLayout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jsp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftLayout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(0, 0, 0))
            );
            leftLayout.setVerticalGroup(
                    leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(jl)
                            .addGap(0, 0, 0)
                            .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addContainerGap())
            );

            javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
            jp.setLayout(jpLayout);
            jpLayout.setHorizontalGroup(
                    jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLayout.createSequentialGroup()
                            .addGap(82, 82, 82)
                            .addComponent(left, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addGap(331, 331, 331))
            );
            jpLayout.setVerticalGroup(
                    jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(left, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
            );
            jp.setBackground(new Color(234, 236, 242));
            AllChat.add(jp);
        } else {
            JPanel jp = new JPanel();
            RightArrowBubble right = new RightArrowBubble();
            JScrollPane jsp = new JScrollPane() {

                @Override
                protected void processMouseWheelEvent(MouseWheelEvent e) {
                    if (!isWheelScrollingEnabled()) {
                        if (getParent() != null) {
                            getParent().dispatchEvent(
                                    SwingUtilities.convertMouseEvent(this, e, getParent()));
                        }
                        return;
                    }
                    super.processMouseWheelEvent(e);
                }

            };
            JTextPane jtp = new JTextPane();
            right.setMaximumSize(new java.awt.Dimension(333, 30000));
            right.setMinimumSize(new java.awt.Dimension(52, 38));
            right.setPreferredSize(new java.awt.Dimension(222, 38));

            jsp.setBackground(new java.awt.Color(133, 90, 168));
            jsp.setBorder(null);
            jsp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jsp.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            jsp.setWheelScrollingEnabled(false);

            jtp.setEditable(false);
            jtp.setBackground(new java.awt.Color(241, 245, 234));
            jtp.setBorder(null);
            jtp.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jtp.setContentType("text/html");
            jtp.setText("<div align=\"right\"> <font size=\"4\" style=\"color:black\";font-family:tahoma;>" + message + "</p> </div>");
            jsp.setViewportView(jtp);

            JLabel jl = new JLabel(user, SwingConstants.RIGHT);
            jl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

            javax.swing.GroupLayout rightLayout = new javax.swing.GroupLayout(right);
            right.setLayout(rightLayout);
            rightLayout.setHorizontalGroup(
                    rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightLayout.createSequentialGroup()
                            .addGap(0, 0, 0)
                            .addComponent(jl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(50, 50, 50))
            );
            rightLayout.setVerticalGroup(
                    rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rightLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(jl)
                            .addGap(0, 0, 0)
                            .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                            .addContainerGap())
            );

            javax.swing.GroupLayout jpLayout = new javax.swing.GroupLayout(jp);
            jp.setLayout(jpLayout);
            jpLayout.setHorizontalGroup(
                    jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLayout.createSequentialGroup()
                            .addGap(331, 331, 331)
                            .addComponent(right, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addGap(82, 82, 82))
            );
            jpLayout.setVerticalGroup(
                    jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(right, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
            );
            jp.setBackground(new Color(234, 236, 242));
            AllChat.add(jp);
        }
        AllChat.revalidate();
        Resize();
        AllChatScroll.getVerticalScrollBar().setValue(AllChatScroll.getVerticalScrollBar().getMaximum());
    }

    private ImageIcon newRoundIcon(String path, int radius) {
        URL resource = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(radius, radius, java.awt.Image.SCALE_SMOOTH);
        Image b = new ImageIcon(newimg).getImage();
        BufferedImage a = imageToBufferedImage(newimg, radius, radius);
        a = makeRoundedCorner(a, radius);
        return new ImageIcon(a);
    }

    private ImageIcon newIcon(String path, int height, int width) {
        URL resource = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    public BufferedImage imageToBufferedImage(Image im, int w, int h) {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    public BufferedImage makeRoundedCorner(BufferedImage image, int radius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Float(0, 0, radius, radius));

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

    private void createUserButton(String userName, ImageIcon icon) {
        JButton butt = new JButton(userName);
        butt.setMaximumSize(new Dimension(200, 35));
        butt.setMinimumSize(new Dimension(200, 35));
        butt.setPreferredSize(new Dimension(200, 30));
        butt.setBorderPainted(false);
        butt.setContentAreaFilled(false);
        butt.setHorizontalAlignment(SwingConstants.LEFT);
        butt.setIcon(icon);
        butt.setIconTextGap(12);
        contacts_panel.add(butt);
        butt.setFont(new Font("Arial", Font.PLAIN, 16));
        butt.setVerticalTextPosition(SwingConstants.BOTTOM);
        butt.setForeground(Color.GREEN);
        butt.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String reciever = ((JButton) evt.getSource()).getText();
                privatewindow[PrivateWindowCount++] = new Private();
                privatewindow[PrivateWindowCount - 1].chatclient = Chat_Client.this;
                privatewindow[PrivateWindowCount - 1].setReciver(reciever);
                privatewindow[PrivateWindowCount - 1].show();
                privatewindow[PrivateWindowCount - 1].requestFocus();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                butt.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                butt.setForeground(Color.GREEN);
            }
        });
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int Port) {
        this.Port = Port;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//<editor-fold defaultstate="collapsed" desc="room button">
    /*
    private void createRoomButton(String userName, ImageIcon icon) {
    JButton butt = new JButton(userName);
    butt.setMaximumSize(new Dimension(200, 40));
    butt.setMinimumSize(new Dimension(200, 40));
    butt.setPreferredSize(new Dimension(200, 40));
    butt.setBorderPainted(false);
    butt.setContentAreaFilled(false);
    butt.setHorizontalAlignment(SwingConstants.LEFT);
    butt.setIcon(icon);
    butt.setIconTextGap(12);
    }
    
    private void RemoveUserButton(String buttonName) {
    for (int i = 0; i < contacts_panel.getComponents().length; i++) {
    if (contacts_panel.getComponent(i).getName().equals(buttonName)) {
    contacts_panel.remove(contacts_panel.getComponent(i));
    }
    }
    }
     */
//</editor-fold>
    public class IncomingReader implements Runnable {

        @Override
        @SuppressWarnings({"deprecation", "ValueOfIncrementOrDecrementUsed"})
        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split("¥");

                    if (data[2].equals(chat)) {
//<editor-fold defaultstate="collapsed" desc="Replace Emojicons">
                        if (data[1].contains(":)") && data[1].contains(":))") == false && data[1].contains(":)]") == false) {
                            data[1] = data[1].replace(":)", "<img src = " + getClass().getResource("/image/1.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":(") && data[1].contains(":((") == false) {
                            data[1] = data[1].replace(":(", "<img src = " + getClass().getResource("/image/2.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(";)") && data[1].contains(";;)") == false) {
                            data[1] = data[1].replace(";)", "<img src = " + getClass().getResource("/image/3.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":D") && data[1].contains(">:D<") == false) {
                            data[1] = data[1].replace(":D", "<img src = " + getClass().getResource("/image/4.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(";;)")) {
                            data[1] = data[1].replace(";;)", "<img src = " + getClass().getResource("/image/5.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(">:D<")) {
                            data[1] = data[1].replace(">:D<", "<img src = " + getClass().getResource("/image/6.gif") + " width = '50' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":-/")) {
                            data[1] = data[1].replace(":-/", "<img src = " + getClass().getResource("/image/7.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":x")) {
                            data[1] = data[1].replace(":x", "<img src = " + getClass().getResource("/image/8.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":\">")) {
                            data[1] = data[1].replace(":\">", "<img src = " + getClass().getResource("/image/9.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":P")) {
                            data[1] = data[1].replace(":P", "<img src = " + getClass().getResource("/image/10.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":-*")) {
                            data[1] = data[1].replace(":-*", "<img src = " + getClass().getResource("/image/11.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("=((")) {
                            data[1] = data[1].replace("=((", "<img src = " + getClass().getResource("/image/12.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":-O")) {
                            data[1] = data[1].replace(":-O", "<img src = " + getClass().getResource("/image/13.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("X(")) {
                            data[1] = data[1].replace("X(", "<img src = " + getClass().getResource("/image/14.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":>")) {
                            data[1] = data[1].replace(":>", "<img src = " + getClass().getResource("/image/15.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("B-)")) {
                            data[1] = data[1].replace("B-)", "<img src = " + getClass().getResource("/image/16.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":-S") && data[1].contains("#:-S") == false) {
                            data[1] = data[1].replace(":-S", "<img src = " + getClass().getResource("/image/17.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("#:-S")) {
                            data[1] = data[1].replace("#:-S", "<img src = " + getClass().getResource("/image/18.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(">:)")) {
                            data[1] = data[1].replace(">:)", "<img src = " + getClass().getResource("/image/19.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":((")) {
                            data[1] = data[1].replace(":((", "<img src = " + getClass().getResource("/image/20.gif") + " width = '25' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":))")) {
                            data[1] = data[1].replace(":))", "<img src = " + getClass().getResource("/image/21.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":|")) {
                            data[1] = data[1].replace(":|", "<img src = " + getClass().getResource("/image/22.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("/:)")) {
                            data[1] = data[1].replace("/:)", "<img src = " + getClass().getResource("/image/23.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("=))")) {
                            data[1] = data[1].replace("=))", "<img src = " + getClass().getResource("/image/24.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("O:-)")) {
                            data[1] = data[1].replace("O:-)", "<img src = " + getClass().getResource("/image/25.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":-B")) {
                            data[1] = data[1].replace(":-B", "<img src = " + getClass().getResource("/image/26.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("=;")) {
                            data[1] = data[1].replace("=;", "<img src = " + getClass().getResource("/image/27.gif") + " width = '20' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":-c")) {
                            data[1] = data[1].replace(":-c", "<img src = " + getClass().getResource("/image/101.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains(":)]")) {
                            data[1] = data[1].replace(":)]", "<img src = " + getClass().getResource("/image/100.gif") + " width = '40' height = '20'> &nbsp;");
                        }
                        if (data[1].contains("~X(")) {
                            data[1] = data[1].replace("~X(", "<img src = " + getClass().getResource("/image/102.gif") + " width = '50' height = '20'> &nbsp;");
                        }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="URL Regex">
                        String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
                        CreateTextMessage(data[0], data[1].replace("\n", "<br />"));
//</editor-fold>
                    } else if (data[2].equals(connect)) {

                        userAdd(data[0]);

                    } else if (data[2].equals(disconnect)) {

                        userRemove(data[0]);

                    } else if (data[2].equals(done)) {
                        contacts_panel.removeAll();
                        writeUsers();
                        userList.clear();

                    } else if (data[2].equals("private")) {
                        boolean alreadyOpen = false;
                        String sender = data[0];
                        String reciever = data[1].substring(0, data[1].indexOf("~"));
                        String message = data[1].substring(data[1].indexOf("~")+1);
                        for (int i = 0; i < PrivateWindowCount; i++) {
                            if (privatewindow[i].getReciver().equals(data[0])) {
                                privatewindow[i].CreateTextMessage(sender, message);
                                privatewindow[i].show();
                                privatewindow[i].requestFocus();
                                alreadyOpen = true;
                                break;
                            }
                        }

                        if (!alreadyOpen) {
                            privatewindow[PrivateWindowCount++] = new Private();
                            privatewindow[PrivateWindowCount - 1].chatclient = Chat_Client.this;
                            privatewindow[PrivateWindowCount - 1].setReciver(sender);
                            privatewindow[PrivateWindowCount - 1].CreateTextMessage(sender, message);
                            privatewindow[PrivateWindowCount - 1].show();
                            privatewindow[PrivateWindowCount - 1].requestFocus();
                        }
                    }

                }
            } catch (IOException ex) {
            }
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String data) {
        userList.add(data);

    }

    public void userRemove(String data) {
        CreateTextMessage("Admin", data + "has disconnected.");
        //chatTextArea.setText(data + " has disconnected.\n");
    }

    public void writeUsers() {
        String[] tempList = new String[(userList.size())];
        userList.toArray(tempList);
        for (String token : tempList) {
            createUserButton(token, newRoundIcon("/image/no-avatar.png", 27));
        }

    }

    public void Connect() {
        if (isConnected == false) {
            try {
                UName.setText(getUsername());
                sock = new Socket(getServerIP(), getPort());
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(getUsername() + "¥has connected.¥Connect"); // Displays to everyone that user connected.
                writer.flush(); // flushes the buffer
                isConnected = true; // Used to see if the client is connected.
            } catch (IOException ex) {
                CreateTextMessage("Admin", "Cannot Connect! Try Again.");
            }
            ListenThread();
        } else if (isConnected == true) {
            CreateTextMessage("Admin", "You are already connected.");
        }
    }

    public void sendDisconnect() {

        String bye = (getUsername() + "¥ ¥Disconnect");
        try {
            writer.println(bye); // Sends server the disconnect signal.
            writer.flush(); // flushes the buffer
        } catch (Exception e) {
            CreateTextMessage("Admin", "Could not send Disconnect message.");
        }

    }

    public void Disconnect() {

        try {
            CreateTextMessage("Admin", "You have been Disconnected.");
            sock.close();
        } catch (IOException ex) {
            CreateTextMessage("Admin", "Failed to disconnect.");
        }
        isConnected = false;
        contacts_panel.removeAll();

    }

    public boolean IsMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    protected void RemovePrivateWindow(String ToUserName) {
        int m_UserIndex = 0;
        for (int i = 0; i < PrivateWindowCount; i++) {
            m_UserIndex++;
            if (privatewindow[i].getReciver().equals(ToUserName)) {
                break;
            }
        }
        for (int m_iLoop = m_UserIndex; m_iLoop < PrivateWindowCount; m_iLoop++) {
            privatewindow[m_iLoop] = privatewindow[m_iLoop + 1];
        }

        PrivateWindowCount--;
    }

    //<editor-fold defaultstate="collapsed" desc="run">
    /*    public void run() {
    while (thread != null) {
    try {
    String stream = datainputstream.readLine();
    if (stream.startsWith("ROCO")) {
    SplitString = stream.substring(5, stream.indexOf("~"));
    tappanel.TxtUserCount.setText("Total Users in " + SplitString + " : " + stream.substring(stream.indexOf("~") + 1));
    }
    if (stream.startsWith("PRIV")) {
    SplitString = stream.substring(5, stream.indexOf(":"));
    if (!(tappanel.UserCanvas.IsIgnoredUser(SplitString))) {
    boolean alreadyOpen = false;
    for (int i = 0; i < PrivateWindowCount; i++) {
    if (privatewindow[i].UserName.equals(SplitString)) {
    privatewindow[i].AddMessageToMessageCanvas(stream.substring(5));
    privatewindow[i].show();
    privatewindow[i].requestFocus();
    alreadyOpen = true;
    break;
    }
    }
    
    if (!(alreadyOpen)) {
    if (PrivateWindowCount >= MAX_PRIVATE_WINDOW) {
    messagecanvas.AddMessageToMessageObject("You are Exceeding private window limit! So you may lose some message from your friends!", MESSAGE_TYPE_ADMIN);
    } else {
    privatewindow[PrivateWindowCount++] = new PrivateChat(this, SplitString);
    privatewindow[PrivateWindowCount - 1].AddMessageToMessageCanvas(stream.substring(5));
    privatewindow[PrivateWindowCount - 1].show();
    privatewindow[PrivateWindowCount - 1].requestFocus();
    }
    }
    
    }
    }
    } catch (Exception _Exc) {
    messagecanvas.AddMessageToMessageObject(_Exc.getMessage(), MESSAGE_TYPE_ADMIN);
    QuitConnection(QUIT_TYPE_DEFAULT);
    }
    }
    }*/
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="comment">
    /*    private void EnablePrivateWindow(String ToUserName) {
    for (int i = 0; i < PrivateWindowCount; i++) {
    if (privatewindow[i].UserName.equals(ToUserName)) {
    privatewindow[i].messagecanvas.AddMessageToMessageObject(ToUserName + " is Currently Online!", MESSAGE_TYPE_ADMIN);
    privatewindow[i].EnableAll();
    return;
    }
    }
    }*/
//</editor-fold>
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        MainPanel = new javax.swing.JPanel();
        topleftpanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        hsep2 = new javax.swing.JSeparator();
        searchtextfield = new chats.customui.RoundJTextField();
        leftpanel = new javax.swing.JPanel();
        lhScrollpane = new javax.swing.JScrollPane();
        contacts_panel = new javax.swing.JPanel();
        toppanel = new javax.swing.JPanel();
        hsep = new javax.swing.JSeparator();
        Avatar = new javax.swing.JLabel();
        txtCount = new javax.swing.JLabel();
        UName = new javax.swing.JLabel();
        AllChatScroll = new javax.swing.JScrollPane();
        AllChat = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        hsep1 = new javax.swing.JSeparator();
        ChatPanel = new javax.swing.JPanel();
        inputfield = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        emojiButton = new javax.swing.JButton();
        attachButton = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 618, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 358, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(830, 517));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                MainPanelComponentResized(evt);
            }
        });

        topleftpanel.setBackground(new java.awt.Color(0, 168, 211));
        topleftpanel.setAlignmentX(1.0F);
        topleftpanel.setAlignmentY(1.0F);
        topleftpanel.setOpaque(false);
        topleftpanel.setPreferredSize(new java.awt.Dimension(0, 0));
        topleftpanel.setLayout(new javax.swing.BoxLayout(topleftpanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBackground(new java.awt.Color(39, 45, 52));
        jPanel4.setMinimumSize(new java.awt.Dimension(200, 59));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 59));

        hsep2.setBackground(new java.awt.Color(52, 62, 69));
        hsep2.setForeground(new java.awt.Color(52, 62, 69));
        hsep2.setOpaque(true);
        hsep2.setRequestFocusEnabled(false);
        hsep2.setVerifyInputWhenFocusTarget(false);

        searchtextfield.setText("Search");
        searchtextfield.setColor_bottom_gradient(new java.awt.Color(156, 156, 156));
        searchtextfield.setColor_top_gradient(new java.awt.Color(176, 176, 176));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hsep2)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(searchtextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(searchtextfield, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addComponent(hsep2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        topleftpanel.add(jPanel4);

        leftpanel.setBackground(new java.awt.Color(39, 45, 52));
        leftpanel.setPreferredSize(new java.awt.Dimension(0, 0));

        lhScrollpane.setBackground(new java.awt.Color(246, 251, 254));
        lhScrollpane.setBorder(null);
        lhScrollpane.setForeground(new java.awt.Color(246, 251, 254));
        lhScrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lhScrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        lhScrollpane.setAlignmentX(0.0F);
        lhScrollpane.setAlignmentY(0.0F);
        lhScrollpane.setPreferredSize(new java.awt.Dimension(199, 199));

        contacts_panel.setBackground(new java.awt.Color(39, 45, 52));
        contacts_panel.setLayout(new javax.swing.BoxLayout(contacts_panel, javax.swing.BoxLayout.PAGE_AXIS));
        lhScrollpane.setViewportView(contacts_panel);

        javax.swing.GroupLayout leftpanelLayout = new javax.swing.GroupLayout(leftpanel);
        leftpanel.setLayout(leftpanelLayout);
        leftpanelLayout.setHorizontalGroup(
            leftpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftpanelLayout.createSequentialGroup()
                .addComponent(lhScrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        leftpanelLayout.setVerticalGroup(
            leftpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftpanelLayout.createSequentialGroup()
                .addComponent(lhScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        toppanel.setBackground(new java.awt.Color(246, 251, 254));
        toppanel.setForeground(new java.awt.Color(246, 251, 254));
        toppanel.setPreferredSize(new java.awt.Dimension(465, 48));

        hsep.setBackground(new java.awt.Color(240, 244, 247));
        hsep.setForeground(new java.awt.Color(240, 244, 247));
        hsep.setOpaque(true);
        hsep.setRequestFocusEnabled(false);
        hsep.setVerifyInputWhenFocusTarget(false);

        Avatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Avatar.setAlignmentX(0.5F);

        txtCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtCount.setMaximumSize(new java.awt.Dimension(500, 14));
        txtCount.setMinimumSize(new java.awt.Dimension(0, 14));

        UName.setFont(new java.awt.Font("Tw Cen MT", 1, 30)); // NOI18N
        UName.setText("Toan");
        UName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout toppanelLayout = new javax.swing.GroupLayout(toppanel);
        toppanel.setLayout(toppanelLayout);
        toppanelLayout.setHorizontalGroup(
            toppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hsep)
            .addGroup(toppanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 42, Short.MAX_VALUE)
                .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        toppanelLayout.setVerticalGroup(
            toppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toppanelLayout.createSequentialGroup()
                .addGroup(toppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, toppanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, toppanelLayout.createSequentialGroup()
                        .addGroup(toppanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Avatar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hsep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        AllChatScroll.setBorder(null);

        AllChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AllChatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AllChatMouseExited(evt);
            }
        });
        AllChat.setLayout(new javax.swing.BoxLayout(AllChat, javax.swing.BoxLayout.PAGE_AXIS));
        AllChatScroll.setViewportView(AllChat);

        hsep1.setBackground(new java.awt.Color(230, 233, 235));
        hsep1.setForeground(new java.awt.Color(230, 233, 235));
        hsep1.setMaximumSize(new java.awt.Dimension(32767, 2));
        hsep1.setMinimumSize(new java.awt.Dimension(0, 2));
        hsep1.setOpaque(true);
        hsep1.setRequestFocusEnabled(false);
        hsep1.setVerifyInputWhenFocusTarget(false);

        ChatPanel.setBackground(new java.awt.Color(255, 255, 255));

        inputfield.setBorder(null);
        inputfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inputfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inputfieldFocusLost(evt);
            }
        });
        inputfield.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputfieldKeyPressed(evt);
            }
        });

        sendButton.setBorder(null);
        sendButton.setBorderPainted(false);
        sendButton.setContentAreaFilled(false);
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonMouseClicked(evt);
            }
        });

        emojiButton.setBorder(null);
        emojiButton.setBorderPainted(false);
        emojiButton.setContentAreaFilled(false);
        emojiButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emojiButtonMouseClicked(evt);
            }
        });

        attachButton.setBorder(null);
        attachButton.setBorderPainted(false);
        attachButton.setContentAreaFilled(false);

        javax.swing.GroupLayout ChatPanelLayout = new javax.swing.GroupLayout(ChatPanel);
        ChatPanel.setLayout(ChatPanelLayout);
        ChatPanelLayout.setHorizontalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(attachButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inputfield, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(emojiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ChatPanelLayout.setVerticalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChatPanelLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputfield, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(emojiButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(attachButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hsep1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ChatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(hsep1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ChatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(leftpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(topleftpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(toppanel, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                            .addComponent(AllChatScroll)))))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topleftpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toppanel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MainPanelLayout.createSequentialGroup()
                        .addComponent(AllChatScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(leftpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MainPanelComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MainPanelComponentResized
        Resize();
    }//GEN-LAST:event_MainPanelComponentResized

    private void AllChatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllChatMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_AllChatMouseEntered

    private void AllChatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllChatMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_AllChatMouseExited

    private void emojiButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emojiButtonMouseClicked
        // TODO add your handling code here:

        if (pan == null) {
            pan = new EmojiconPanel(this);
        }
        PopupFactory factory = PopupFactory.getSharedInstance();
        popup = factory.getPopup(Chat_Client.this, pan, emojiButton.getLocationOnScreen().x - pan.getPreferredSize().height + 20, emojiButton.getLocationOnScreen().y - pan.getPreferredSize().height);

        pan.addFocusListener(new FocusListener() {
            private boolean gained = false;

            @Override
            public void focusGained(FocusEvent e) {
                gained = true;
            }

            @Override
            public void focusLost(FocusEvent e) {
//                popup.hide();
            }
        });
        popup.show();
        pan.requestFocusInWindow();
    }//GEN-LAST:event_emojiButtonMouseClicked

    private void sendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonMouseClicked
        String nothing = "";
        if ((inputfield.getText()).equals(nothing)) {
            inputfield.setText("");
            inputfield.requestFocus();
        } else {
            try {
                writer.println(getUsername() + "¥" + inputfield.getText() + "¥" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                System.out.println(ex);
                CreateTextMessage("Admin", "Message was not sent.");
//chatTextArea.setText(chatTextArea.getText() + "Message was not sent. \n");
            }
            inputfield.setText("");
            inputfield.requestFocus();
        }

        inputfield.setText("");
        inputfield.requestFocus();
    }//GEN-LAST:event_sendButtonMouseClicked

    private void inputfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputfieldKeyPressed
        if (evt.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER) {
            String nothing = "";
            if ((inputfield.getText()).equals(nothing)) {
                inputfield.setText("");
                inputfield.requestFocus();
            } else {
                try {
                    writer.println(getUsername() + "¥" + inputfield.getText() + "¥" + "Chat");
                    writer.flush(); // flushes the buffer
                } catch (Exception ex) {
                    CreateTextMessage("Admin", "Message was not sent");
                }
                inputfield.setText("");
                inputfield.requestFocus();
            }

            inputfield.setText("");
            inputfield.requestFocus();
        }
    }//GEN-LAST:event_inputfieldKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            sendDisconnect();
            Disconnect();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }//GEN-LAST:event_formWindowClosing

    private void inputfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputfieldFocusGained
        if (inputfield.getText().equals("Type message")) {
            inputfield.setText("");
            Font font = inputfield.getFont();
            inputfield.setFont(font.deriveFont(Font.PLAIN));
            inputfield.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_inputfieldFocusGained

    private void inputfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputfieldFocusLost
        if (inputfield.getText().isEmpty()) {
            inputfield.setText("Type message");
            Font font = inputfield.getFont();
            inputfield.setFont(font.deriveFont(Font.ITALIC));
            inputfield.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_inputfieldFocusLost

    public void clicked(String buttname) {
        switch (buttname) {
            case "0":
                inputfield.setText(inputfield.getText() + ":)");
                break;
            case "1":
                inputfield.setText(inputfield.getText() + ":(");
                break;
            case "2":
                inputfield.setText(inputfield.getText() + ";)");
                break;
            case "3":
                inputfield.setText(inputfield.getText() + ":D");
                break;
            case "4":
                inputfield.setText(inputfield.getText() + ";;)");
                break;
            case "5":
                inputfield.setText(inputfield.getText() + ">:D<");
                break;
            case "6":
                inputfield.setText(inputfield.getText() + ":-/");
                break;
            case "7":
                inputfield.setText(inputfield.getText() + ":x");
                break;
            case "8":
                inputfield.setText(inputfield.getText() + ":\">");
                break;
            case "9":
                inputfield.setText(inputfield.getText() + ":P");
                break;
            case "10":
                inputfield.setText(inputfield.getText() + ":-*");
                break;
            case "11":
                inputfield.setText(inputfield.getText() + "=((");
                break;
            case "12":
                inputfield.setText(inputfield.getText() + ":-O");
                break;
            case "13":
                inputfield.setText(inputfield.getText() + "X(");
                break;
            case "14":
                inputfield.setText(inputfield.getText() + ":>");
                break;
            case "15":
                inputfield.setText(inputfield.getText() + "B-)");
                break;
            case "16":
                inputfield.setText(inputfield.getText() + ":-S");
                break;
            case "17":
                inputfield.setText(inputfield.getText() + "#:-S");
                break;
            case "18":
                inputfield.setText(inputfield.getText() + ">:)");
                break;
            case "19":
                inputfield.setText(inputfield.getText() + ":((");
                break;
            case "20":
                inputfield.setText(inputfield.getText() + ":))");
                break;
            case "21":
                inputfield.setText(inputfield.getText() + ":|");
                break;
            case "22":
                inputfield.setText(inputfield.getText() + "/:)");
                break;
            case "23":
                inputfield.setText(inputfield.getText() + "=))");
                break;
            case "24":
                inputfield.setText(inputfield.getText() + "O:-)");
                break;
            case "25":
                inputfield.setText(inputfield.getText() + ":-B");
                break;
            case "26":
                inputfield.setText(inputfield.getText() + "=;");
                break;
            case "27":
                inputfield.setText(inputfield.getText() + ":-c");
                break;
            default:
                break;
        }
//<editor-fold defaultstate="collapsed" desc="Hinh sai vi tri">
        /* else if (buttname.equals("28")) {
            inputfield.setText(inputfield.getText() + ":)]");
        }else if (buttname.equals("29")) {
        inputfield.setText(inputfield.getText() + "~X(");
        }else if (buttname.equals("30")) {
        inputfield.setText(inputfield.getText() + ":-h");
        }else if (buttname.equals("31")) {
        inputfield.setText(inputfield.getText() + ":-t");
        }else if (buttname.equals("32")) {
        inputfield.setText(inputfield.getText() + "8->");
        }else if (buttname.equals("33")) {
        inputfield.setText(inputfield.getText() + "|-)");
        }else if (buttname.equals("34")) {
        inputfield.setText(inputfield.getText() + "8-|");
        }else if (buttname.equals("35")) {
        inputfield.setText(inputfield.getText() + "L-)");
        }else if (buttname.equals("36")) {
        inputfield.setText(inputfield.getText() + ":-&");
        }else if (buttname.equals("37")) {
        inputfield.setText(inputfield.getText() + ":-$");
        }else if (buttname.equals("38")) {
        inputfield.setText(inputfield.getText() + "[-(");
        }else if (buttname.equals("39")) {
        inputfield.setText(inputfield.getText() + ":O)");
        }else if (buttname.equals("40")) {
        inputfield.setText(inputfield.getText() + "8-}");
        }else if (buttname.equals("41")) {
        inputfield.setText(inputfield.getText() + "<:-P");
        }else if (buttname.equals("42")) {
        inputfield.setText(inputfield.getText() + "(:|");
        }else if (buttname.equals("43")) {
        inputfield.setText(inputfield.getText() + "=P~");
        }else if (buttname.equals("44")) {
        inputfield.setText(inputfield.getText() + ":-?");
        }else if (buttname.equals("45")) {
        inputfield.setText(inputfield.getText() + "#-o");
        }else if (buttname.equals("46")) {
        inputfield.setText(inputfield.getText() + "=D>");
        }else if (buttname.equals("47")) {
        inputfield.setText(inputfield.getText() + ":-SS");
        }*/
//</editor-fold>
        popup.hide();
    }

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            String laf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(laf);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Chat_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new Chat_Client().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AllChat;
    private javax.swing.JScrollPane AllChatScroll;
    private javax.swing.JLabel Avatar;
    private javax.swing.JPanel ChatPanel;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JLabel UName;
    private javax.swing.JButton attachButton;
    private javax.swing.JPanel contacts_panel;
    private javax.swing.JButton emojiButton;
    private javax.swing.JSeparator hsep;
    private javax.swing.JSeparator hsep1;
    private javax.swing.JSeparator hsep2;
    private javax.swing.JTextField inputfield;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel leftpanel;
    private javax.swing.JScrollPane lhScrollpane;
    private chats.customui.RoundJTextField searchtextfield;
    private javax.swing.JButton sendButton;
    private javax.swing.JPanel topleftpanel;
    private javax.swing.JPanel toppanel;
    private javax.swing.JLabel txtCount;
    // End of variables declaration//GEN-END:variables
}
