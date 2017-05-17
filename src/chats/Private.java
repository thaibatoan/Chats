package chats;

import chats.customui.LeftArrowBubble;
import chats.customui.RightArrowBubble;
import chats.panels.EmojiconPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

public class Private extends javax.swing.JFrame {

    public Chat_Client chatclient;
    private String reciver;
    private Popup popup;
    private EmojiconPanel pan;

    public Private() {
        initComponents();
        textFieldInit();
        buttonInit();
        Resize();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                try {
                    ExitPrivateWindow();
                }
                catch(Exception ex){
                    setVisible(false);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AllChatScroll = new javax.swing.JScrollPane();
        AllChat = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        hsep1 = new javax.swing.JSeparator();
        ChatPanel = new javax.swing.JPanel();
        attachButton = new javax.swing.JButton();
        inputfield = new javax.swing.JTextField();
        emojiButton = new javax.swing.JButton();
        sendButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 400));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

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

        attachButton.setBorderPainted(false);
        attachButton.setContentAreaFilled(false);

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

        emojiButton.setBorderPainted(false);
        emojiButton.setContentAreaFilled(false);
        emojiButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emojiButtonMouseClicked(evt);
            }
        });

        sendButton.setBorderPainted(false);
        sendButton.setContentAreaFilled(false);
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ChatPanelLayout = new javax.swing.GroupLayout(ChatPanel);
        ChatPanel.setLayout(ChatPanelLayout);
        ChatPanelLayout.setHorizontalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(attachButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inputfield, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emojiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ChatPanelLayout.setVerticalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChatPanelLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
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
            .addComponent(ChatPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(hsep1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ChatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AllChatScroll)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(AllChatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonInit() {
        sendButton.setIcon(newIcon("/image/send-512.png", sendButton.getSize().height, sendButton.getSize().height));
        sendButton.setFocusPainted(false);
        emojiButton.setFocusPainted(false);
        attachButton.setFocusPainted(false);
        emojiButton.setIcon(newIcon("/image/emoji.png", sendButton.getSize().height, sendButton.getSize().height));
        attachButton.setIcon(newIcon("/image/attachment.png", sendButton.getSize().height, sendButton.getSize().height));
    }

    private void textFieldInit() {
        Font font = inputfield.getFont();
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

    public void CreateTextMessage(String user, String message) {
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
        } else if (user.equals(chatclient.getUsername())) {
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

    private void ExitPrivateWindow() {
        chatclient.RemovePrivateWindow(reciver);
    }

    private void inputfieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputfieldKeyPressed
        if (evt.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER) {
            String nothing = "";
            if ((inputfield.getText()).equals(nothing)) {
                inputfield.setText("");
                inputfield.requestFocus();
            } else {
                try {
                    chatclient.writer.println(chatclient.getUsername() + "¥" + getReciver() + "~" + inputfield.getText() + "¥" + "private");
                    chatclient.writer.flush(); // flushes the buffer
                    CreateTextMessage(reciver, reciver);
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

    private void sendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonMouseClicked
        String nothing = "";
        if ((inputfield.getText()).equals(nothing)) {
            inputfield.setText("");
            inputfield.requestFocus();
        } else {
            try {
                chatclient.writer.println(chatclient.getUsername() + "¥" + getReciver() + "~" + inputfield.getText() + "¥" + "private");
                System.out.println(chatclient.getUsername() + "¥" + getReciver() + "~" + inputfield.getText() + "¥" + "private");
                chatclient.writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                CreateTextMessage("Admin", "Message was not sent");
            }
            inputfield.setText("");
            inputfield.requestFocus();
        }

        inputfield.setText("");
        inputfield.requestFocus();
    }//GEN-LAST:event_sendButtonMouseClicked

    private void emojiButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emojiButtonMouseClicked
        // TODO add your handling code here:

        if (pan == null) {
            pan = new EmojiconPanel(this);
        }
        PopupFactory factory = PopupFactory.getSharedInstance();
        popup = factory.getPopup(Private.this, pan, emojiButton.getLocationOnScreen().x - pan.getPreferredSize().height + 20, emojiButton.getLocationOnScreen().y - pan.getPreferredSize().height);

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

    private void AllChatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllChatMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_AllChatMouseEntered

    private void AllChatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllChatMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_AllChatMouseExited

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

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        Resize();
    }//GEN-LAST:event_formComponentResized

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Private.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Private().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AllChat;
    private javax.swing.JScrollPane AllChatScroll;
    private javax.swing.JPanel ChatPanel;
    private javax.swing.JButton attachButton;
    private javax.swing.JButton emojiButton;
    private javax.swing.JSeparator hsep1;
    private javax.swing.JTextField inputfield;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the reciver
     */
    public String getReciver() {
        return reciver;
    }

    /**
     * @param reciver the reciver to set
     */
    public void setReciver(String reciver) {
        this.reciver = reciver;
    }
}
