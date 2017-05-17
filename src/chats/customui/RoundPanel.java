package chats.customui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class RoundPanel extends JPanel {

    private int curve;
    private ShapeType Shape;
    private Shape shape;
    private float[] frac;
    private Color[] colors;
    private Image imageBackground;

    public RoundPanel() {
        this.colors = new Color[]{new Color(200, 220, 220), new Color(0, 60, 90)};
        this.frac = new float[]{0f, 1f};
        this.Shape = ShapeType.Ellipse;
        this.curve = 20;
    }

    public int getCurve() {
        return curve;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }

    public ShapeType getShape() {
        return Shape;
    }

    public void setShape(ShapeType Shape) {
        this.Shape = Shape;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    public float[] getFrac() {
        return frac;
    }

    public void setFrac(float[] frac) {
        this.frac = frac;
    }

    public Image getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(Image imageBackground) {
        this.imageBackground = imageBackground;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        RenderingHints qualityHints
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        //<editor-fold defaultstate="collapsed" desc="Fill Shape">
        LinearGradientPaint lgp = new LinearGradientPaint(
                new Point(0, 0), new Point(getWidth(), getHeight()), frac, colors);
        g2.setPaint(lgp);

        if (getShape() != null) {
            switch (getShape()) {
                case Ellipse:
                    g2.fillOval(0, 0, this.getWidth(), this.getHeight());
                    break;
                case Rectangle:
                    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
                    break;
                case RoundRect:
                    g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), getCurve(), getCurve());
                    break;
                default:
                    break;
            }
        }
        //</editor-fold>
        if (getImageBackground() != null) {
            g2.drawImage(imageBackground, 
                    (this.getWidth() - imageBackground.getWidth(this))/2, 
                    (this.getHeight()- imageBackground.getHeight(this))/2, 
                    this);
        }
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null
                || !shape.getBounds().equals(getBounds())) {
            switch (getShape()) {
                case Ellipse:
                    shape = new Ellipse2D.Float(0, 0,
                            getWidth(), getHeight());
                    break;
                case Rectangle:
                    shape = new Rectangle2D.Float(0, 0,
                            getWidth(), getHeight());
                    break;
                case RoundRect:
                    shape = new RoundRectangle2D.Float(0, 0,
                            getWidth(), getHeight(), curve, curve);
                    break;
                default:
                    break;
            }
        }
        return shape.contains(x, y);
    }
}
