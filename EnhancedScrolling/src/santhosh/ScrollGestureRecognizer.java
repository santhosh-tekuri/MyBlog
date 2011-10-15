package santhosh;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.io.FileReader;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ScrollGestureRecognizer implements AWTEventListener{
    private static ScrollGestureRecognizer instance = new ScrollGestureRecognizer();

    private ScrollGestureRecognizer(){
        start();
    }

    public static ScrollGestureRecognizer getInstance(){
        return instance;
    }

    public void start(){
        Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
    }

    public void stop(){
        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }

    public void eventDispatched(AWTEvent event){
        MouseEvent me = (MouseEvent)event;
        if(me.getID()==MouseEvent.MOUSE_PRESSED)
            System.out.println("ScrollGestureRecognizer.eventDispatched");
        boolean isGesture = SwingUtilities.isMiddleMouseButton(me) && me.getID()==MouseEvent.MOUSE_PRESSED;
        if(!isGesture)
            return;

        Component comp = me.getComponent();
        comp = SwingUtilities.getDeepestComponentAt(comp, me.getX(), me.getY());
        JViewport viewPort = (JViewport)SwingUtilities.getAncestorOfClass(JViewport.class, comp);
        if(viewPort==null)
            return;
        JRootPane rootPane = SwingUtilities.getRootPane(viewPort);
        if(rootPane==null)
            return;

        Point location = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), rootPane.getGlassPane());
        ScrollGlassPane glassPane = new ScrollGlassPane(rootPane.getGlassPane(), viewPort, location);
        rootPane.setGlassPane(glassPane);
        glassPane.setVisible(true);
    }
}