package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmptyTablePopupDemo{
    private static JScrollPane createScroll(Component comp){
        JScrollPane scroll = new JScrollPane(comp);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }
    
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JTable table1 = new JTable(0, 5);
        JTable table2 = new JTable(0, 5){
            public boolean getScrollableTracksViewportHeight() {
                return getPreferredSize().height < getParent().getHeight();
            }
        };
        
        MouseListener mouseListener = new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                showPopup(e);
            }

            public void mouseReleased(MouseEvent e){
                showPopup(e);
            }

            private void showPopup(MouseEvent e){
                if(!e.isPopupTrigger())
                    return;

                JPopupMenu popup = new JPopupMenu();
                popup.add("Add Rows");
                popup.add("Paste");
                popup.addSeparator();
                popup.add("Action1");
                popup.add("Action2");
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        };        
        table1.addMouseListener(mouseListener);
        table2.addMouseListener(mouseListener);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT
                , createScroll(table1), createScroll(table2));
        split.setResizeWeight(0.5d);
        JFrame frame = new JFrame("JPopupMenu on Empty JTable - santhosh@in.fiorano.com");
        frame.getContentPane().add(split);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setVisible(true);
    }
}