package santhosh;

import net.miginfocom.swing.MigLayout;
               
import javax.swing.*;
import java.awt.*;

public class MigLayoutTest extends JFrame{
    public MigLayoutTest(){
        super("MigLayout Demo");
        Container contents = getContentPane();
        contents.setLayout(new MigLayout("debug 100", "[grow 1][grow 3]", "[][]"));
        contents.add(new JButton("North"), "dock north");
        contents.add(new JButton("East"), "dock east");
        contents.add(new JButton("South"), "dock south");
        contents.add(new JButton("West"),  "dock west");
        contents.add(new JButton("Button5"));
        contents.add(new JButton("Button6"));
        contents.add(new JButton("Button7"), "newline");
        contents.add(new JButton("Button8"));
    }

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        test=1;
        MigLayoutTest frame = new MigLayoutTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.show();
    }
}
