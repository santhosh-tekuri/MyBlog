package santhosh;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class TitledPanel extends JPanel{
    JPanel contents = new JPanel();
    Border border = new TitledBorder(new EtchedBorder());

    public TitledPanel(){
        super(new BorderLayout());
    }

    public JPanel getContentPane(){
        return contents;
    }

    public Insets getInsets(){
        return border.getBorderInsets(this);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();

        TitledPanel titlePanel = new TitledPanel();
        JPanel panel = titlePanel.getContentPane();
        panel.add(new JButton("fjdkfjdkf"));
        panel.add(new JButton("fjdkfjdkf"));
        panel.add(new JButton("fjdkfjdkf"));
        panel.add(new JButton("fjdkfjdkf"));
        frame.getContentPane().add(titlePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
