/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chats.customui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.JPanel;

/**
 *
 * @author toan
 */
public class RightArrowBubble extends JPanel {

    private int strokeThickness = 5;
    private int padding = strokeThickness / 2;
    private int arrowSize = 6;
    private int radius = 10;
    private Color color = new Color(241, 245, 234);
    private float mul = 2;

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(qualityHints);
        graphics2D.setPaint(color);
        int width = getWidth();
        int height = getHeight();
        GeneralPath path = new GeneralPath();
        path.moveTo(width - mul*5, mul*15);
        path.curveTo(width - mul*5, mul*15, width - mul*7, mul*5, width, 0);
        path.curveTo(width, 0, width - mul*12, 0, width - mul*12, mul*5);
        path.curveTo(width - mul*12, mul*5, width - mul*12, 0, width - mul*20, 0);
        path.lineTo(mul*10, 0);
        path.curveTo(mul*10, 0, 0, 0, 0, mul*10);
        path.lineTo(0, height - mul*10);
        path.curveTo(0, height - mul*10, 0, height, mul*10, height);
        path.lineTo(width - mul*15, height);
        path.curveTo(width - mul*15, height, width - mul*5, height, width - mul*5, height - mul*10);
        path.lineTo(width - mul*5, mul*15);
        path.closePath();
        graphics2D.fill(path);
    }
}
