package santhosh;

import javax.swing.*;
import java.awt.*;

public class XMLEditorTest{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        XMLEditor editor = new XMLEditor();
        editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.width-=100;
        dim.height-=100;
        editor.setSize(dim);
        editor.setVisible(true);
    }
}
