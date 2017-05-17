package chats.customui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.Border;

public class RoundJPasswordField extends JPasswordField {

    private Shape shape;

    private Icon icon;
    private final Insets dummyInsets;
    private int curve = 20;
    private Color line_top_gradient = new Color(240, 248, 255, 255).brighter();
    private Color line_bottom_gradient = new Color(167, 207, 215, 255).brighter();
    private Color color_top_gradient = new Color(240, 248, 255, 127).brighter();
    private Color color_bottom_gradient = new Color(167, 207, 215, 255).brighter();

    public RoundJPasswordField() {
        super(30);
        this.icon = null;

        Border border = UIManager.getBorder("TextField.border");
        JTextField dummy = new JTextField();
        this.dummyInsets = border.getBorderInsets(dummy);
        setOpaque(false); // As suggested by @AVD in comment.
    }

    public int getCurve() {
        return curve;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }

    public Color getLine_top_gradient() {
        return line_top_gradient;
    }

    public void setLine_top_gradient(Color line_top_gradient) {
        this.line_top_gradient = line_top_gradient;
    }

    public Color getLine_bottom_gradient() {
        return line_bottom_gradient;
    }

    public void setLine_bottom_gradient(Color line_bottom_gradient) {
        this.line_bottom_gradient = line_bottom_gradient;
    }

    public Color getColor_top_gradient() {
        return color_top_gradient;
    }

    public void setColor_top_gradient(Color color_top_gradient) {
        this.color_top_gradient = color_top_gradient;
    }

    public Color getColor_bottom_gradient() {
        return color_bottom_gradient;
    }

    public void setColor_bottom_gradient(Color color_bottom_gradient) {
        this.color_bottom_gradient = color_bottom_gradient;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return this.icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        RenderingHints qualityHints
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.setPaint(new GradientPaint(new Point(0, 0), color_top_gradient, new Point(0, getHeight()),
                color_bottom_gradient));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), curve, curve);
        g2.dispose();
        int textX = 2;

        if (this.icon != null) {
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = dummyInsets.left + curve / 4;//this is our icon's x
            textX = x + iconWidth + 2; //this is the x where text should start
            int y = (this.getHeight() - iconHeight) / 2;
            icon.paintIcon(this, g, x, y);
            g.setColor(line_bottom_gradient.darker());
            g.drawLine(textX + 6, 0, textX + 6, this.getHeight());
        }
        setMargin(new Insets(2, textX + 15, 2, 2));
        super.paintComponent(g);

    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        RenderingHints qualityHints
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);
        g2.setPaint(new GradientPaint(new Point(0, 0), line_top_gradient, new Point(0, getHeight()),
                line_bottom_gradient));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, curve, curve);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
        return shape.contains(x, y);
    }
}
