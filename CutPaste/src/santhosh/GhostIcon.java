package santhosh;

import javax.swing.*;
import java.awt.*;

public class GhostIcon implements Icon{
    private static final Composite composite
            = AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f);

    private Icon delegate;

    public GhostIcon(Icon delegate){
        this.delegate = delegate;
    }

    public int getIconHeight(){
        return delegate.getIconHeight();
    }

    public int getIconWidth(){
        return delegate.getIconWidth();
    }

    public void paintIcon(Component c, Graphics g, int x, int y){
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints oldHints = g2.getRenderingHints();
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        Composite oldComposite = g2.getComposite();
        g2.setComposite(composite);
        delegate.paintIcon(c, g, x, y);
        g2.setComposite(oldComposite);
        g2.setRenderingHints(oldHints);
    }
}
