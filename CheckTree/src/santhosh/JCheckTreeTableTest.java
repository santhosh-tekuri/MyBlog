package santhosh;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.*;
import javax.swing.tree.TreeNode;

public class JCheckTreeTableTest{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        DefaultTreeTableModel treeTableModel =
                new DefaultTreeTableModel(((TreeNode)new JTree().getModel().getRoot()));

        final JXTreeTable treeTable = new JXTreeTable(treeTableModel);
        CheckTreeManager manager = new CheckTreeTableManager(treeTable);

        JFrame frame = new JFrame("How to use CheckTree with JXTreeTable - santhosh@in.fiorano.com");
        frame.getContentPane().add(new JScrollPane(treeTable));
        frame.pack();
        frame.setVisible(true);
    }
}
