package chats.customui;

import chats.customui.ImageHelper;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Framinator {


    // Some constants which define the transition direction
    public static final int TRANSITION_LEFT = 0;
    public static final int TRANSITION_RIGHT = 1;

    public static void transitionComponents(final Container parent, Component from, final Component to, int direction) {
        // First check we have a CardLayout, if not: return
        final CardLayout cl;
        if (parent.getLayout() instanceof CardLayout) {
            cl = (CardLayout) parent.getLayout();
        } else {
            return;
        }

        // Create the combined image depending on the direction
        BufferedImage combined;
        switch (direction) {
            default:
            case TRANSITION_LEFT:
                combined = ImageHelper.combineImages(
                        ImageHelper.captureComponent(to),
                        ImageHelper.captureComponent(from),
                        ImageHelper.SIDE_BY_SIDE);
                break;
            case TRANSITION_RIGHT:
                combined = ImageHelper.combineImages(
                        ImageHelper.captureComponent(from),
                        ImageHelper.captureComponent(to),
                        ImageHelper.SIDE_BY_SIDE);
                break;
        }

        // Create the intermediary transition panel
        JPanel transition = new JPanel(new GridLayout());
        transition.add(new JLabel(new ImageIcon(combined)));
        final JScrollPane scroller = new JScrollPane(transition);
        // Hide the scroll bars
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the scroller to the CardLayout and show it
        parent.add(scroller, "transitionCard");
        cl.show(parent, "transitionCard");

        // Prepare the Timer and the callback task
        Timer scrollTimer = new Timer();
        TimerTask callback = new TimerTask() {
            @Override
            public void run() {
                // Once the ScrollTransition has finished:
                // Remove the intermediary transition Card and show the next Card
                parent.remove(scroller);
                parent.add(to, "newCard");
                cl.show(parent, "newCard");
            }
        };

        // Start the ScrollTransition
        ScrollTransition scrollTask = new ScrollTransition(scrollTimer, callback, scroller, 20, direction);
        scrollTimer.scheduleAtFixedRate(scrollTask, 0, 30);
    }

    private static class ScrollTransition extends TimerTask {

        private Timer timer;
        private TimerTask callback;
        private JScrollPane scroller;
        private int step;
        private int direction;
        private int $offset = 0;
        private int $max = 0;
        private boolean $runOnce = true;

        ScrollTransition(Timer timer, TimerTask callback, JScrollPane scroller, int step, int direction) {
            this.timer = timer;
            this.callback = callback;
            this.scroller = scroller;
            this.step = step;
            this.direction = direction;
            $max = scroller.getHorizontalScrollBar().getMaximum();
        }

        @Override
        public void run() {
            switch (direction) {
                default:
                case TRANSITION_LEFT:
                    // Going Right-to-Left
                    // Start at the end, scroll backwards
                    if ($runOnce) {
                        scroller.getHorizontalScrollBar().setValue($max);
                        $offset = $max;
                        $runOnce = false;
                    }

                    // If there is space for a step, scroll left one step
                    // Else scroll right to the left
                    if ($offset > (0 + step)) {
                        $offset -= step;
                        scroller.getHorizontalScrollBar().setValue($offset);
                    } else {
                        scroller.getHorizontalScrollBar().setValue(0);
                        // Cancel the current ScrollTransition
                        cancel();
                        timer.schedule(callback, 0);
                    }
                    break;

                case TRANSITION_RIGHT:
                    // Going Left-to-Right
                    // If there is space for a step, scroll right one step
                    // Else scroll right to the end
                    if ($offset < ($max - step)) {
                        $offset += step;
                        scroller.getHorizontalScrollBar().setValue($offset);
                    } else {
                        scroller.getHorizontalScrollBar().setValue($max);
                        // Cancel the current ScrollTransition
                        cancel();
                        timer.schedule(callback, 0);
                    }
                    break;
            }
        }

    }
}
