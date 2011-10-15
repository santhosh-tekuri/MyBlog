package santhosh;

import java.awt.*;

public class ComponentZoomable implements Zoomable{
    protected Component comp;

    public ComponentZoomable(Component comp) {
        this.comp = comp;
    }

    public void zoom(float factor) {
        Font font = comp.getFont();
        float scaledSize = font.getSize2D() + (font.getSize2D() * factor);
        if(scaledSize>=1)
            comp.setFont(font.deriveFont(scaledSize));
    }

    public static int apply(int value, float factor) {
        float newValue = value + (value * factor);
        return (int) (factor < 0 ? Math.floor(newValue) : Math.ceil(newValue));
    }
}