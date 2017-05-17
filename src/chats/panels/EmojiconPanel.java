package chats.panels;

import chats.Chat_Client;
import chats.Private;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class EmojiconPanel extends javax.swing.JPanel implements MouseListener, FocusListener {

    private JFrame parent;

    public EmojiconPanel(JFrame dad) {
        initComponents();
        parent = dad;
        addMouseListener(this);
        addFocusListener(this);
        for (int i = 0; i < 28; i++) {
            int x = i / 5;
            int y = i % 5;
            JButton butt = new JButton("");
            butt.setPreferredSize(new Dimension(40, 40));
            butt.setMaximumSize(new Dimension(40, 40));
            butt.setMinimumSize(new Dimension(40, 40));
            butt.setBorder(BorderFactory.createEmptyBorder());
            butt.setBorderPainted(false);
            butt.setName("" + i);
            butt.addActionListener((ActionEvent e) -> {
                JButton button = (JButton) e.getSource();
                if (parent instanceof Chat_Client) {
                    ((Chat_Client)parent).clicked(button.getName());
                }
                else if (parent instanceof Private){
                    ((Private)parent).clicked(button.getName());
                }
            });

            butt.setIcon(new ImageIcon(getClass().getResource("/image/" + (i + 1) + ".gif")));
            AllChat.add(butt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10 + y * 40, 10 + x * 40, -1, -1));
        }
    }

    private ImageIcon newIcon(String path, int height, int width) {
        URL resource = getClass().getResource(path);
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AllChatScroll = new javax.swing.JScrollPane();
        AllChat = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMaximumSize(new java.awt.Dimension(235, 235));
        setMinimumSize(new java.awt.Dimension(235, 235));
        setPreferredSize(new java.awt.Dimension(235, 235));

        AllChatScroll.setBorder(null);
        AllChatScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        AllChatScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        AllChat.setMaximumSize(new java.awt.Dimension(195, 32767));
        AllChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AllChatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AllChatMouseExited(evt);
            }
        });
        AllChat.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        AllChatScroll.setViewportView(AllChat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AllChatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AllChatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AllChatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllChatMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_AllChatMouseEntered

    private void AllChatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AllChatMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_AllChatMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AllChat;
    private javax.swing.JScrollPane AllChatScroll;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mousePressed(MouseEvent me) {
        requestFocus();
        System.out.println("Mouse pressed on box");
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void focusGained(FocusEvent fe) {
        System.out.println("Focus gained by box");
    }

    @Override
    public void focusLost(FocusEvent fe) {
        System.out.println("Focus lost by box");
    }
}
