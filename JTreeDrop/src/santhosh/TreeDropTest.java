package santhosh;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author santhosh kumar T
 */
public class TreeDropTest{
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

    private static void expandAll(JTree tree){
        for(int i=0; i<tree.getRowCount(); i++)
            tree.expandRow(i);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JTree tree1 = new JTree();
        tree1.setDragEnabled(true);
        tree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        applyDNDHack(tree1);
        expandAll(tree1);

        JTree tree2 = new JTree();
        expandAll(tree2);
new DropTarget(tree2, new TreeDropListener(){
    public void drop(DropTargetDropEvent dtde){
        super.drop(dtde);
        if(treePath==null){
            dtde.rejectDrop();
            return;
        }

        JTree tree = (JTree)dtde.getDropTargetContext().getComponent();
        DefaultTreeModel treeModel = (DefaultTreeModel)tree.getModel();
        try{
            String data = (String)dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
            DefaultMutableTreeNode dragNode = new DefaultMutableTreeNode(data);
            DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
            DefaultMutableTreeNode dropParent = (DefaultMutableTreeNode)dropNode.getParent();
            if(dropParent==null){
                dtde.rejectDrop();
                return;
            }
            int dropIndex = treeModel.getIndexOfChild(dropParent, dropNode);
            if(before==null){
                treeModel.removeNodeFromParent(dropNode);
                treeModel.insertNodeInto(dragNode, dropParent, dropIndex);
            }else if(before.equals(Boolean.TRUE)){
                treeModel.insertNodeInto(dragNode, dropParent, dropIndex);
            }else{
                if(dropIndex<dropParent.getChildCount()){
                    treeModel.insertNodeInto(dragNode, dropParent, dropIndex+1);
                }else{
                    dropParent.add(dragNode);
                    treeModel.nodesWereInserted(dropParent, new int[dropIndex+1]);
                }
            }
            tree.setSelectionPath(new TreePath(dragNode.getPath()));
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
});

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createScroll(tree1, "DragSource"), createScroll(tree2, "DropTarget"));
        split.setResizeWeight(0.5d);

        JFrame frame = new JFrame("Visual Clues for JTree DND - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(split);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
