package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil{
    private static JLabel imgObserver = new JLabel();
    public static Image createGhostImage(Image img){
        BufferedImage ghost = new BufferedImage(img.getWidth(imgObserver)
                , img.getHeight(imgObserver), BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g2 = ghost.createGraphics();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f));
        g2.drawImage(img, 0, 0, ghost.getWidth(), ghost.getHeight(), imgObserver);
        g2.dispose();
        return ghost;
    }
}