package santhosh;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener{
    protected CheckTreeSelectionModel selectionModel;
    protected JTree tree = new JTree();
    protected boolean showRootNodeCheckBox;
    int hotspot = new JCheckBox().getPreferredSize().width;

    public CheckTreeManager(JTree tree, boolean dig, boolean showRootNodeCheckBox){
        this.tree = tree;
        this.showRootNodeCheckBox = showRootNodeCheckBox;
        selectionModel = new CheckTreeSelectionModel(tree.getModel(), dig);
        setCellRenderer();
        tree.addMouseListener(this);
        selectionModel.addTreeSelectionListener(this);
    }

    protected void setCellRenderer(){
        tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel, showRootNodeCheckBox));
    }

    protected void treeChanged(){
        tree.treeDidChange();
    }

    JLabel label = new JLabel("Selection:");
    public void mouseClicked(MouseEvent me){
        TreePath path = tree.getPathForLocation(me.getX(), me.getY());
        if(path==null)
            return;
        if(me.getX()>tree.getPathBounds(path).x+hotspot)
            return;

        boolean selected = selectionModel.isPathSelected(path, selectionModel.isDigged());
        if(selectionModel.isDigged() && !selected && selectionModel.isPartiallySelected(path))
            selected = !selected;
        selectionModel.removeTreeSelectionListener(this);

        try{
            if(selected)
                selectionModel.removeSelectionPath(path);
            else
                selectionModel.addSelectionPath(path);
        } finally{
            selectionModel.addTreeSelectionListener(this);
            treeChanged();
            TreePath[] selectionPaths = selectionModel.getSelectionPaths();
            if(selectionPaths==null)
                label.setText("Selection:");
            else{
                StringBuffer buf = new StringBuffer();
                for(int i = 0; i<selectionPaths.length; i++)
                    buf.append(selectionPaths[i].getLastPathComponent().toString()+", ");
                String text = buf.toString();
                label.setText("Selection: "+ text.substring(0, text.length()-2));
            }
        }
    }

    public CheckTreeSelectionModel getSelectionModel(){
        return selectionModel;
    }

    public void valueChanged(TreeSelectionEvent e){
        tree.treeDidChange();
    }
}