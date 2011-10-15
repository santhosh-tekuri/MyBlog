package santhosh;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author santhosh kumar T
 */
public class ListDropTest{
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
        scroll.setColumnHeaderView(new JLabel(name));
        return scroll;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JList list1 = new JList(new String[]{"item1", "item2", "item3", "item4"});
        list1.setDragEnabled(true);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        applyDNDHack(list1);

        final DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("item5");
        listModel.addElement("item6");
        listModel.addElement("item7");
        listModel.addElement("item8");
        listModel.addElement("item9");

        JList list2 = new JList(listModel);
        new DropTarget(list2, new ListDropListener(){
            public void drop(DropTargetDropEvent dtde){
                super.drop(dtde);
                JList list = (JList)dtde.getDropTargetContext().getComponent();
                try{
                    String data = (String)dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    if(before==null){
                        listModel.setElementAt(data, listIndex);
                        list.setSelectedIndex(listIndex);
                    }else if(before.equals(Boolean.TRUE)){
                        listModel.insertElementAt(data, listIndex);
                        list.setSelectedIndex(listIndex);
                    }else{
                        if(listIndex<listModel.size()){
                            listModel.insertElementAt(data, listIndex+1);
                            list.setSelectedIndex(listIndex+1);
                        }else{
                            listModel.addElement(data);
                            list.setSelectedIndex(listModel.getSize()-1);
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createScroll(list1, "DragSource"), createScroll(list2, "DropTarget"));
        split.setResizeWeight(0.5d);

        JFrame frame = new JFrame("Visual Clues for JList DND - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(split);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
