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
public class LeftArrowBubble extends JPanel {

    private int strokeThickness = 5;
    private final int padding = getStrokeThickness() / 2;
    private int radius = 10;
    private int arrowSize = 6;
    private Color color = new Color(133, 90, 168);
    private float mul = 2;

    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(qualityHints);
        graphics2D.setPaint(getColor());
        int width = getWidth();
        int height = getHeight();
        GeneralPath path = new GeneralPath();
        path.moveTo(getMul() * 5, getMul() * 10);
        path.curveTo(getMul() * 5, getMul() * 10, getMul() * 7, getMul() * 5, 0, 0);
        path.curveTo(0, 0, getMul() * 12, 0, getMul() * 12, getMul() * 5);
        path.curveTo(getMul() * 12, getMul() * 5, getMul() * 12, 0, getMul() * 20, 0);
        path.lineTo(width - getMul() * 10, 0);
        path.curveTo(width - getMul() * 10, 0, width, 0, width, getMul() * 10);
        path.lineTo(width, height - getMul() * 10);
        path.curveTo(width, height - getMul() * 10, width, height, width - getMul() * 10, height);
        path.lineTo(getMul() * 15, height);
        path.curveTo(getMul() * 15, height, getMul() * 5, height, getMul() * 5, height - getMul() * 10);
        path.lineTo(getMul() * 5, getMul() * 15);
        path.closePath();
        graphics2D.fill(path);
    }

    public int getStrokeThickness() {
        return strokeThickness;
    }

    public void setStrokeThickness(int strokeThickness) {
        this.strokeThickness = strokeThickness;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getArrowSize() {
        return arrowSize;
    }

    public void setArrowSize(int arrowSize) {
        this.arrowSize = arrowSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getMul() {
        return mul;
    }

    public void setMul(float mul) {
        this.mul = mul;
    }
}
