package santhosh;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CheckTreeTest{

    private static JPanel createCheckTree(boolean dig){
        JPanel panel = new JPanel(new BorderLayout());
        JTree tree = new JTree();
        CheckTreeManager checkTreeManager = new CheckTreeManager(tree, dig, true);

        for(int i=0; i<tree.getRowCount(); i++)
            tree.expandRow(i);

        panel.add(new JScrollPane(tree), BorderLayout.CENTER);
        panel.add(checkTreeManager.label, BorderLayout.SOUTH);

        // selecting some nodes by default
        TreeModel model = tree.getModel();
//        Object root = model.getRoot();
//        Object colors = model.getChild(root, 0);
//        Object red = model.getChild(colors, 2);
//        Object sports = model.getChild(root, 1);
//        Object football = model.getChild(sports, 2);
//        checkTreeManager.getSelectionModel().setSelectionPaths(new TreePath[]{
//            new TreePath(new Object[]{ root, colors, red }),
//            new TreePath(new Object[]{ root, sports, football }),
//        });

        checkTreeManager.getSelectionModel().setSelectionPaths(new TreePath[]{
            TreePathUtil.deserialize(model, "JTree, colors, red"),
            TreePathUtil.deserialize(model, "JTree, sports, football"),
        });

        return panel;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("DIG=TRUE", createCheckTree(true));
        tabPane.addTab("DIG=FALSE", createCheckTree(false));
        JFrame frame = new JFrame("JTree with CheckBoxes - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tabPane);
        ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    public void addChildPaths(TreePath path, TreeModel model, List result){
        Object item = path.getLastPathComponent();
        int childCount = model.getChildCount(item);
        for(int i = 0; i<childCount; i++)
            result.add(path.pathByAddingChild(model.getChild(item, i)));
    }

    public ArrayList getDescendants(TreePath paths[] , TreeModel model){
        ArrayList result = new ArrayList();
        Stack pending = new Stack();
        pending.addAll(Arrays.asList(paths));
        while(!pending.isEmpty()){
            TreePath path = (TreePath)pending.pop();
            addChildPaths(path, model, pending);
            result.add(path);
        }
        return result;
    }

    public ArrayList getAllCheckedPaths(CheckTreeManager manager, JTree tree){
        return getDescendants(manager.getSelectionModel().getSelectionPaths(), tree.getModel());
    }
}
