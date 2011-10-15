package santhosh;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class ExpansionStateDemo{
    public static void applyDNDHack(JTree tree){
        MouseListener dragListener = null;

        // the default dnd implemntation requires to first select node and then drag
        try{
            Class clazz = Class.forName("javax.swing.plaf.basic.BasicDragGestureRecognizer");
            MouseListener[] mouseListeners = tree.getMouseListeners();
            for(int i = 0; i<mouseListeners.length; i++){
                if(clazz.isAssignableFrom(mouseListeners[i].getClass())){
                    dragListener = mouseListeners[i];
                    break;
                }
            }

            if(dragListener!=null){
                tree.removeMouseListener(dragListener);
                tree.removeMouseMotionListener((MouseMotionListener)dragListener);
                tree.addMouseListener(dragListener);
                tree.addMouseMotionListener((MouseMotionListener)dragListener);
            }
        } catch(ClassNotFoundException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    static int id = 0;
    public static DefaultMutableTreeNode createNode(int level){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(""+ id++);
        if(level==4)
            return node;
        for(int i = 0; i<2; i++)
            node.add(createNode(level+1));
        return node;
    }

    public static void main(String[] args){
        final JTree tree = new JTree(createNode(0));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setDragEnabled(true);
        applyDNDHack(tree);
        tree.setTransferHandler(new TreeTransferHandler());

        JCheckBox cb = new JCheckBox("Retain Expansion State", true);
        cb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ((TreeTransferHandler)tree.getTransferHandler()).retainExpansionState =
                        !((TreeTransferHandler)tree.getTransferHandler()).retainExpansionState;
            }
        });

        for(int i=0; i<tree.getRowCount(); i++)
            tree.expandRow(i);

        JFrame frame = new JFrame("Remembering Tree ExpansionState - santhosh@in.fiorano.com");
        frame.getContentPane().add(cb, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(tree));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
