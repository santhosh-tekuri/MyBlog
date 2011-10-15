package santhosh;

import javax.swing.*;
import javax.swing.text.EditorKit;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jul 23, 2005
 * Time: 2:21:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnhancedScrollingDemo{
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

        JTextArea editor = new JTextArea(20, 70);
        editor.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.out.println("EnhancedScrollingDemo.mouseClicked");
            }
        });
        try{
            new DefaultEditorKit().read(EnhancedScrollingDemo.class.getResourceAsStream("sample.txt")
                    , editor.getDocument(), 0);
        } catch(Exception e){
            e.printStackTrace();
        }
        frame.getContentPane().add(new JScrollPane(editor));
        frame.pack();
        frame.setVisible(true);
    }
}
