package chats.customui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class RoundButton extends JButton {

    private Shape shape;
    private int curve;
    private ShapeType Shape;
    private boolean pressed;
    private float[] frac;
    private Color[] colors;
    private Image imageBackground;
    private AffineTransform identity = new AffineTransform();
    private int angle;

    public RoundButton() {
        this.angle = 0;
        this.colors = new Color[]{new Color(200, 220, 220), new Color(0, 60, 90)};
        this.frac = new float[]{0f, 1f};
        this.pressed = false;
        this.Shape = ShapeType.RoundRect;
        this.curve = 20;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        RenderingHints qualityHints
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        LinearGradientPaint color = new LinearGradientPaint(
                new Point(0, 0), new Point(getWidth(), getHeight()), frac, colors);
        LinearGradientPaint darkercolor = new LinearGradientPaint(
                new Point(0, 0), new Point(getWidth(), getHeight()), frac, getDarker(colors));
        if (pressed) {
            g2.setPaint(darkercolor);
        } else {
            g2.setPaint(color);
        }
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

        if (getImageBackground() != null) {
            AffineTransform trans = new AffineTransform();
            trans.setTransform(identity);
            trans.translate((this.getWidth() - imageBackground.getWidth(this)) / 2, (this.getHeight() - imageBackground.getHeight(this)) / 2);
            trans.rotate(Math.toRadians(angle), imageBackground.getWidth(this) / 2, imageBackground.getHeight(this) / 2);
            g2.drawImage(getImageBackground(), trans, this);
        }
        g2.dispose();
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            g.setColor(getForeground());
            g.drawString(getText(), getWidth() / 2 - fm.stringWidth(getText()) / 2, getHeight() / 2 + fm.getMaxDescent());
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        RenderingHints qualityHints
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        LinearGradientPaint color = new LinearGradientPaint(
                new Point(0, 0), new Point(getWidth(), getHeight()), frac, colors);
        g2.setPaint(color);

        if (getShape() != null) {
            switch (getShape()) {
                case Ellipse:
                    g2.drawOval(0, 0, this.getWidth() - 1, this.getHeight() - 1);
                    break;
                case Rectangle:
                    g2.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
                    break;
                case RoundRect:
                    g2.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, getCurve(), getCurve());
                    break;
                default:
                    break;
            }
        }
        g2.dispose();
        Font f = getFont();
    }

    private Color[] getDarker(Color[] cols) {
        Color[] newcols = new Color[cols.length];
        for (int i = 0; i < cols.length; i++) {
            newcols[i] = cols[i].darker();
        }
        return newcols;
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

    @Override
    public void processMouseEvent(MouseEvent e) {
        Graphics g;
        switch (e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                pressed = true;
                repaint();
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, getLabel()));
                }
                if (pressed == true) {
                    pressed = false;
                    repaint();
                }
                break;
            case MouseEvent.MOUSE_ENTERED:

                break;
            case MouseEvent.MOUSE_EXITED:
                if (pressed == true) {
                    pressed = false;
                    repaint();
                }
                break;
        }
        super.processMouseEvent(e);
    }

    /**
     * @return the frac
     */
    public float[] getFrac() {
        return frac;
    }

    /**
     * @param frac the frac to set
     */
    public void setFrac(float[] frac) {
        this.frac = frac;
    }

    /**
     * @return the colors
     */
    public Color[] getColors() {
        return colors;
    }

    /**
     * @param colors the colors to set
     */
    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    /**
     * @return the angle
     */
    public int getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(int angle) {
        this.angle = angle;
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

    public Image getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(Image imageBackground) {
        this.imageBackground = imageBackground;
    }
}
