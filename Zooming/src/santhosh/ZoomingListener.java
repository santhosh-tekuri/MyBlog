package santhosh;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.*;

public class ZoomingListener implements MouseWheelListener {
    private Zoomable zoomable;

    public ZoomingListener(Zoomable zoomable) {
        this.zoomable = zoomable;
    }

    public void mouseWheelMoved(MouseWheelEvent mwe) {
        Component comp = mwe.getComponent();
        if((mwe.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())!=0)
            zoomable.zoom(mwe.getWheelRotation() * 0.25f);
        else{
            // you need to redispatch event.
            // otherwize component doesn't scroll
            // on scrolling mousewhell
            comp.removeMouseWheelListener(this);
            try {
                comp.dispatchEvent(mwe);
            } finally {
                comp.addMouseWheelListener(this);
            }
        }
    }
}
