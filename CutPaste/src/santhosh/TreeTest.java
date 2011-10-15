package santhosh;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class TreeTest{
    public static void main(String[] args){
        JFrame frame = new JFrame();
        JTree tree = new JTree(){
            public String getToolTipText(MouseEvent event){
                JPopupMenu menu = new JPopupMenu();
                menu.add("popup1");
                return "hello";
            }
        };

        ToolTipManager.sharedInstance().registerComponent(tree);
        tree.setDragEnabled(true);
//        JSplitPane split = new JSplitPane();
//        split.setLeftComponent(new JScrollPane(tree));
//        split.setRightComponent(new JScrollPane(new JTextArea()));
//        frame.getContentPane().add(split);
        frame.getContentPane().add(new JScrollPane(tree));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
