package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

/** @author Santhosh Kumar - santhosh@in.fiorano.com **/
public class ShortCutTest{

    public static void main(String[] args){
        JTextField tf = new JTextField();

        // trap focus traversal keys also
        tf.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        tf.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

        tf.addKeyListener(new KeyListener(){
            int currentKeyCode = 0;
            public void keyPressed(KeyEvent ke){
                ke.consume();
                JTextField tf = (JTextField)ke.getSource();
                tf.setText(toString(ke));
            }

            public void keyReleased(KeyEvent ke){
                ke.consume();
                JTextField tf = (JTextField)ke.getSource();
                switch(currentKeyCode){
                    case KeyEvent.VK_ALT:
                    case KeyEvent.VK_ALT_GRAPH:
                    case KeyEvent.VK_CONTROL:
                    case KeyEvent.VK_SHIFT:
                        tf.setText("");
                        return;
                }
            }

            public void keyTyped(KeyEvent ke){
                ke.consume();
            }
            private String toString(KeyEvent ke){
                int keyCode = currentKeyCode = ke.getKeyCode();
                int modifiers = ke.getModifiers();
                String modifText = KeyEvent.getKeyModifiersText(modifiers);
                if("".equals(modifText))  // no modifiers
                    return KeyEvent.getKeyText(keyCode);
                else switch(keyCode){
                    case KeyEvent.VK_ALT:
                    case KeyEvent.VK_ALT_GRAPH:
                    case KeyEvent.VK_CONTROL:
                    case KeyEvent.VK_SHIFT:
                        return modifText+"+"; // middle of shortcut
                    default:
                        return modifText+"+"+KeyEvent.getKeyText(keyCode);
                }
            }
        });
        JOptionPane.showMessageDialog(null, tf, "ShortCut - santhosh@in.fiorano.com", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
