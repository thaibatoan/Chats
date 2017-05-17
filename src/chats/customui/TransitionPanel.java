package chats.customui;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JPanel;

public class TransitionPanel extends JPanel {

    public TransitionPanel() {
        super.setLayout(new CardLayout());
    }

    public void transitionLeft(Component from, Component to) {
        Framinator.transitionComponents(this, from, to, Framinator.TRANSITION_LEFT);
    }

    public void transitionRight(Component from, Component to) {
        Framinator.transitionComponents(this, from, to, Framinator.TRANSITION_RIGHT);
    }
}