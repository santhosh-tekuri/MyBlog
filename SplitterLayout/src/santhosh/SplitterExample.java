package santhosh;

import javax.swing.*;
import java.awt.*;

public class SplitterExample extends JFrame{
    public SplitterExample(){
        super("SplitterLayout - santhosh@in.fiorano.com");
        JPanel contents = (JPanel)getContentPane();
        contents.setLayout(new SplitterLayout(SplitterLayout.VERTICAL));
        contents.add("1", new JButton("A (1)"));

        JSplitterBar b1 = new JSplitterBar();
        b1.setLayout(new GridLayout(1, 0));
        b1.add(new JSplitterSpace());
        b1.add(new JLabel("Status"));
        b1.add(new JTextField("Enter your name"));
        b1.add(new JSplitterSpace());
        contents.add(b1);
        contents.add("2", new JButton("B (2)"));

        JSplitterBar b2 = new JSplitterBar();
        b2.setLayout(new SplitterLayout(SplitterLayout.HORIZONTAL));
        b2.add("5", new JSplitterSpace());
        b2.add(new JSplitterBar());
        b2.add("10", new JLabel("Status"));
        b2.add(new JSplitterBar());
        b2.add("40", new JTextField("Enter your name"));
        contents.add(b2);
        contents.add("4", new JButton("C (4)"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JFrame frame = new SplitterExample();
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
