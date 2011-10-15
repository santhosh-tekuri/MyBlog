package santhosh;

import javax.swing.*;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class FocusOwnerDemo extends JFrame{
    public FocusOwnerDemo(){
        super("Focus Tracking - santhosh@in.fiorano.com");
        JTitledPanel tp1 = new JTitledPanel("First FileChooser");
        tp1.getContents().add(new JFileChooser());

        JTitledPanel tp2 = new JTitledPanel("Second FileChooser");
        tp2.getContents().add(new JFileChooser());

        JPanel contents = (JPanel)getContentPane();
        contents.setLayout(new GridLayout(2, 1));
        contents.add(tp1);
        contents.add(tp2);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        FocusOwnerDemo demo = new FocusOwnerDemo();
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        if(demo.getSize().height>height-200)
            demo.setSize(demo.getSize().width, height-200);
        demo.setVisible(true);
    }
}
