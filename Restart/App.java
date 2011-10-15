import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

public class App{
    public static void main(String args[]){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ignore){}
        Action restartAction = new AbstractAction("Restart"){
            public void actionPerformed(ActionEvent ae){
                try{
                    new File("restart").createNewFile();
                    System.exit(0);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };

        JFrame frame = new JFrame("App");
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(restartAction);
        menubar.add(menu);
        frame.setJMenuBar(menubar);
        frame.setSize(100, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}