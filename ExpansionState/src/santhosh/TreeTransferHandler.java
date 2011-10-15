package santhosh;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class TreeTransferHandler extends TransferHandler{
    private static Transferable t;
    boolean retainExpansionState = true;
    private String expansionState = null;

    protected Transferable createTransferable(JComponent c){
        JTree tree = (JTree)c;
        int row = tree.getSelectionRows()[0];
        expansionState = TreeUtil.getExpansionState(tree, row);
        System.out.println("expansionState = "+expansionState);
        MutableTreeNode node = (MutableTreeNode)tree.getSelectionPath().getLastPathComponent();
        return t = new ObjectTransferable(node);
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors){
        try{
            JTree tree = (JTree)comp;
            DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
            DefaultMutableTreeNode dragNode = (DefaultMutableTreeNode)t.getTransferData(new ObjectFlavor(DefaultMutableTreeNode.class));
            System.err.println("canImport: "+!dropNode.isNodeAncestor(dragNode));
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean importData(JComponent comp, Transferable t){
        try{
            JTree tree = (JTree)comp;
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            DefaultMutableTreeNode dropNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
            DefaultMutableTreeNode dragNode = (DefaultMutableTreeNode)t.getTransferData(new ObjectFlavor(DefaultMutableTreeNode.class));
            if(dragNode.isNodeDescendant(dropNode)){
                System.err.println("invalid drop");
                return false;
            }
            model.removeNodeFromParent(dragNode);
            dropNode.add(dragNode);
            model.nodesWereInserted(dropNode, new int[]{ dropNode.getChildCount()-1 });
            if(retainExpansionState){
                tree.expandPath(new TreePath(dropNode.getPath()));
                int row = tree.getRowForPath(new TreePath(dragNode.getPath()));
                System.err.println("row:"+row);
                System.err.println("expanstionState: "+expansionState);
                TreeUtil.restoreExpanstionState(tree, row, expansionState);
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
