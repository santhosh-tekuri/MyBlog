package santhosh;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class LinkButtonTest{

    private static final Color LINK_COLOR = Color.blue;
    private static final Border LINK_BORDER = BorderFactory.createEmptyBorder(0, 0, 1, 0);
    private static final Border HOVER_BORDER = BorderFactory.createMatteBorder(0, 0, 1, 0, LINK_COLOR);

    private static class LinkMouseListener extends MouseAdapter{
        public void mouseEntered(MouseEvent e){
            ((JComponent)e.getComponent()).setBorder(HOVER_BORDER);
        }

        public void mouseReleased(MouseEvent e){
            ((JComponent)e.getComponent()).setBorder(LINK_BORDER);
        }

        public void mouseExited(MouseEvent e){
            ((JComponent)e.getComponent()).setBorder(LINK_BORDER);
        }
    }

    public static JButton makeLink(JButton button){
        button.setBorder(LINK_BORDER);
        button.setForeground(LINK_COLOR);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setRequestFocusEnabled(false);
        button.addMouseListener(new LinkMouseListener());
        return button;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JButton button = new JButton("I'm Link Button.");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "Ouch!");
            }
        });

        JFrame frame = new JFrame("LinkButton - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(makeLink(button));
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
