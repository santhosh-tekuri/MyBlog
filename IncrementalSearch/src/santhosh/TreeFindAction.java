package santhosh;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.text.Position;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class TreeFindAction extends FindAction{
    protected boolean changed(JComponent comp, String searchString, Position.Bias bias){
        JTree tree = (JTree)comp;
        boolean startingFromSelection = true;
        int max = tree.getRowCount();
        int increment = 0;
        if(bias!=null)
            increment = (bias == Position.Bias.Forward) ? 1 : -1;
        int startingRow = (tree.getLeadSelectionRow() + increment + max) % max;
        if (startingRow < 0 || startingRow >= tree.getRowCount()) {
            startingFromSelection = false;
            startingRow = 0;
        }

        TreePath path = getNextMatch(tree, searchString, startingRow, bias);
        if (path != null) {
            changeSelection(tree, path);
            return true;
        } else if (startingFromSelection) {
            path = getNextMatch(tree, searchString, 0, bias);
            if (path != null) {
                changeSelection(tree, path);
                return true;
            }
        }
        return false;
    }

    // takes care of modifiers - control
    protected void changeSelection(JTree tree, TreePath path){
        if(controlDown){
            tree.addSelectionPath(path);
        }else
            tree.setSelectionPath(path);
        tree.scrollPathToVisible(path);
    }

    public TreePath getNextMatch(JTree tree, String prefix, int startingRow, Position.Bias bias) {
        int max = tree.getRowCount();
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        if (startingRow < 0 || startingRow >= max) {
            throw new IllegalArgumentException();
        }
        if(ignoreCase)
            prefix = prefix.toUpperCase();

        // start search from the next/previous element froom the
        // selected element
        int increment = (bias==null || bias == Position.Bias.Forward) ? 1 : -1;
        int row = startingRow;
        do {
            TreePath path = tree.getPathForRow(row);
            String text = tree.convertValueToText(
                    path.getLastPathComponent(), tree.isRowSelected(row),
                    tree.isExpanded(row), true, row, false);

            if(ignoreCase)
                text = text.toUpperCase();
            if (text.startsWith(prefix)) {
                return path;
            }
            row = (row + increment + max) % max;
        } while (row != startingRow);
        return null;
    }
}