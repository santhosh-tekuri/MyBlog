package santhosh;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;

public class EnhancedScrollingTest{
    public static void main(String[] args){
        // initializes and installs enhanced scrolling
        ScrollGestureRecognizer.getInstance();

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Enhanced Scrolling - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        for(int i=0; i<50;i++)
            panel.add(new JButton("Button "+i));
        frame.getContentPane().add(new JScrollPane(panel));
        frame.pack();
        frame.setVisible(true);
    }
}
