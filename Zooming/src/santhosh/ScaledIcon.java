package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author Santhosh Kumar T
 */
public class ScaledIcon implements Icon{
    private Icon delegate;
    public ScaledIcon(Icon delegate){
        this.delegate = delegate;
    }

    private float scale = 1;
    public void setFactor(float factor){
        scale += scale*factor;
        System.out.println(factor+"->"+scale);
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.scale(scale, scale);
        g2d.setTransform(transform);
        delegate.paintIcon(c, g, (int)(x/scale), (int)(y/scale));
        g2d.setTransform(original);
    }

    public int getIconWidth(){
        return (int)(delegate.getIconWidth()*scale);
    }

    public int getIconHeight(){
        return (int)(delegate.getIconHeight()*scale);
    }
}
