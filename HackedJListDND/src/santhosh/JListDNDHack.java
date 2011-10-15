package santhosh;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 */
public class JListDNDHack{
    public static void applyDNDHack(JComponent comp){
        MouseListener dragListener = null;

        // the default dnd implemntation requires to first select node and then drag
        try{
            Class clazz = Class.forName("javax.swing.plaf.basic.BasicDragGestureRecognizer");
            MouseListener[] mouseListeners = comp.getMouseListeners();
            for(int i = 0; i<mouseListeners.length; i++){
                if(clazz.isAssignableFrom(mouseListeners[i].getClass())){
                    dragListener = mouseListeners[i];
                    break;
                }
            }

            if(dragListener!=null){
                comp.removeMouseListener(dragListener);
                comp.removeMouseMotionListener((MouseMotionListener)dragListener);
                comp.addMouseListener(dragListener);
                comp.addMouseMotionListener((MouseMotionListener)dragListener);
            }
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static JScrollPane createScroll(JComponent comp, String name){
        JScrollPane scroll = new JScrollPane(comp);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setColumnHeaderView(new JLabel("  "+name));
        return scroll;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignore){
        }

        JList hackedList = new JList(new String[]{"item1", "item2", "item3", "item4"});
//        applyDNDHack(hackedList);
        hackedList.setDragEnabled(true);
        hackedList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JList normalList = new JList(new String[]{"item1", "item2", "item3", "item4"});
        normalList.setDragEnabled(true);
        normalList.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createScroll(hackedList, "Hacked JList"), createScroll(normalList, "Normal JList"));
        split.setResizeWeight(0.5d);
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split, createScroll(new JTextArea(), "Drop Items here"));
        split.setResizeWeight(0.7);

        JFrame frame = new JFrame("JList DND Hack - santhosh@in.fiorano.com");
        frame.getContentPane().add(split);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}