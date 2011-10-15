package santhosh;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.*;

public class ToolbarToolTips{
/**
 * converts KeyStroke to readable string format.
 * this can be used to show the accelerator along with the tooltip for
 * toolbar buttons
 *
 * this code is extracted from javax.swing.plaf.basic.BasicMenuItemUI class
 */
public static String getAcceleratorText(KeyStroke accelerator){
    String acceleratorDelimiter = UIManager.getString("MenuItem.acceleratorDelimiter");
    if(acceleratorDelimiter==null)
        acceleratorDelimiter = "+";

    String acceleratorText = "";
    if (accelerator != null) {
        int modifiers = accelerator.getModifiers();
        if (modifiers > 0) {
            acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
            acceleratorText += acceleratorDelimiter;
        }

        int keyCode = accelerator.getKeyCode();
        if (keyCode!=0)
            acceleratorText += KeyEvent.getKeyText(keyCode);
        else
            acceleratorText += accelerator.getKeyChar();
    }
    return acceleratorText;
}

public static Action enableAcceleratedTooltips(Action action){
    String accelerator = getAcceleratorText((KeyStroke)action.getValue(Action.ACCELERATOR_KEY));
    action.putValue(Action.SHORT_DESCRIPTION, action.getValue(Action.NAME)+"   "+accelerator);
    return action;
}

    private static Action createAction(){
        Action action = new AbstractAction("Settings"){
            public void actionPerformed(ActionEvent e){
            }
        };
        action.putValue(Action.SHORT_DESCRIPTION, action.getValue(Action.NAME));
        action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        action.putValue(Action.SMALL_ICON, UIManager.getIcon("FileChooser.newFolderIcon"));
        return action;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.add(createAction());
        toolbar.add(enableAcceleratedTooltips(createAction()));
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}