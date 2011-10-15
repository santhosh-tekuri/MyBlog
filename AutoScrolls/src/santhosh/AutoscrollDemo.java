package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.dnd.*;

public class AutoscrollDemo{
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

    public static JScrollPane createScroll(JComponent comp, String name){
        JScrollPane scroll = new JScrollPane(comp);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setColumnHeaderView(new JLabel(name));
        return scroll;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        DefaultListModel model = new DefaultListModel();
        for(int i = 0; i<100; i++)
            model.addElement("Item "+(i+1));

        JList list1 = new JList(model);
        JList list2 = new JList(model);
        JList list3 = new MyList(model);

        applyDNDHack(list1);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JSplitPane split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createScroll(list2, "Standard JList"), createScroll(list3, "MyList with Autoscroll"));
        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createScroll(list1, "DragSource"), split1);
        split1.setBorder(BorderFactory.createEmptyBorder());
        split2.setBorder(BorderFactory.createEmptyBorder());
        split1.setResizeWeight(0.5d);
        split2.setResizeWeight(0.5d);

        list1.setDragEnabled(true);

        JFrame frame = new JFrame("Autoscrolling - santhosh@in.fiorano.com");
        frame.getContentPane().add(split2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setVisible(true);
    }
}

class MyList extends JList implements Autoscroll{
    AutoscrollSupport scrollSupport = new AutoscrollSupport(this, new Insets(10, 10, 10, 10));

    public MyList(ListModel model){
        super(model);
    }

    public Insets getAutoscrollInsets(){
        return scrollSupport.getAutoscrollInsets();
    }

    public void autoscroll(Point cursorLocn){
        scrollSupport.autoscroll(cursorLocn);
    }
}

