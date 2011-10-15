package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Stack;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ActiveWindowTracker{
    static Stack showingWindows = new Stack();

    private static WindowListener windowListener = new WindowAdapter(){
        public void windowDeactivated(WindowEvent we){
            if(!we.getWindow().isShowing())
                windowHiddenOrClosed(we);
        }

        public void windowClosed(WindowEvent we){
            windowHiddenOrClosed(we);
        }

        private void windowHiddenOrClosed(WindowEvent we){
            we.getWindow().removeWindowListener(windowListener);
            showingWindows.remove(we.getWindow());
        }
    };

    private static PropertyChangeListener propListener = new PropertyChangeListener(){
        public void propertyChange(PropertyChangeEvent evt){
            if(evt.getNewValue()!=null){
                Window window = (Window)evt.getNewValue();
                if(!showingWindows.contains(window)){
                    window.addWindowListener(windowListener);
                    showingWindows.remove(window);
                }
                showingWindows.push(window);
            }
        }
    };

    static{
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("activeWindow", propListener);
    }

    public static Window findActiveWindow(){
        if(!showingWindows.isEmpty())
            return (Window)showingWindows.peek();
        else{
            // Trick to get the shared frame instance.
            JDialog dlg = new JDialog();
            Window owner = new JDialog().getOwner();
            dlg.dispose();
            return owner;
        }
    }

    public static void main(String[] args){
        ActiveWindowTracker.class.getClass();
        JOptionPane.showMessageDialog(null, "fdfdfd");
        System.err.println("activew:"+findActiveWindow());
    }
}