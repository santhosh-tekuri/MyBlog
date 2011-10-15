package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NumLockTest{

    public static void main(String[] args){
        final JTree tree = new JTree();

        Action prevAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(!Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK))
                    return;
                tree.getActionMap().get("selectPrevious").actionPerformed(e);
            }
        };
        tree.registerKeyboardAction(prevAction, KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), JComponent.WHEN_FOCUSED);

        Action nextAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(!Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK))
                    return;
                tree.getActionMap().get("selectNext").actionPerformed(e);
            }
        };
        tree.registerKeyboardAction(nextAction, KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), JComponent.WHEN_FOCUSED);

        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(tree));
        frame.pack();
        frame.setVisible(true);
    }
}
