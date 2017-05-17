package chats.customui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageHelper {

    public static final int SIDE_BY_SIDE = 0;

    public static BufferedImage captureComponent(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        component.paint(image.getGraphics());
        return image;
    }

    public static BufferedImage combineImages(BufferedImage img1, BufferedImage img2, int renderHint) {
        switch (renderHint) {
            default:
            case SIDE_BY_SIDE:
                // Create a new image that is the width of img1+img2
                // Take the height of the taller image
                // Paint the two images side-by-side
                BufferedImage combined = new BufferedImage(img1.getWidth() + img2.getWidth(),
                        Math.max(img1.getHeight(), img2.getHeight()), BufferedImage.TYPE_INT_ARGB);
                Graphics g = combined.getGraphics();
                g.drawImage(img1, 0, 0, null);
                g.drawImage(img2, img1.getWidth(), 0, null);
                return combined;
        }
    }
}

