package santhosh;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.*;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class JCheckTreeTableTest{
    private static JPanel createCheckTreeTable(boolean dig){
        JPanel panel = new JPanel(new BorderLayout());
        TreeTableModel model = new TestTreeTableModel(new JTree().getModel().getRoot());
        final JXTreeTable treeTable = new JXTreeTable(model);
        CheckTreeManager manager = new CheckTreeTableManager(treeTable, dig, true);


        panel.add(new JScrollPane(treeTable), BorderLayout.CENTER);
        panel.add(manager.label, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("DIG=TRUE", createCheckTreeTable(true));
        tabPane.addTab("DIG=FALSE", createCheckTreeTable(false));
        JFrame frame = new JFrame("How to use CheckTree with JXTreeTable - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tabPane);
        ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(300, 400);
        frame.setVisible(true);
    }
}

class TestTreeTableModel extends AbstractTreeTableModel{
    TestTreeTableModel(Object root){
        super(root);
    }

    public int getColumnCount(){
        return 4;
    }

    public Object getValueAt(Object node, int column){
        return column==0 ? node : node+":col"+column;
    }

    public Object getChild(Object o, int i){
        return ((TreeNode)o).getChildAt(i);
    }

    public int getChildCount(Object o){
        return ((TreeNode)o).getChildCount();
    }

    public int getIndexOfChild(Object o, Object o2){
        return ((TreeNode)o).getIndex((TreeNode)o2);
    }
}