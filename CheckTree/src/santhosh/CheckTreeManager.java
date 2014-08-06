package santhosh;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener, ActionListener{
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
        tree.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_FOCUSED);
        selectionModel.addTreeSelectionListener(this);
    }

    protected CheckTreeCellRenderer renderer;
    protected void setCellRenderer(){
        tree.setCellRenderer(renderer=new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel, showRootNodeCheckBox));
    }

    protected void treeChanged(){
        tree.treeDidChange();
    }

    private VetoableChangeListener changeListener;
    public void setVetoableChangeListener(VetoableChangeListener changeListener){
        this.changeListener = changeListener;
    }

    public static interface CheckBoxCustomizer{
        public boolean showCheckBox(TreePath path);
    }

    private CheckBoxCustomizer checkBoxCustomer = null;
    public void setCheckBoxCustomer(CheckBoxCustomizer checkBoxCustomer){
        this.checkBoxCustomer = checkBoxCustomer;
        renderer.checkBoxCustomer = checkBoxCustomer;
    }

    public void toggleSelection(TreePath path){
        if(path==null)
            return;

        boolean selected = selectionModel.isPathSelected(path, selectionModel.isDigged());
        if(selectionModel.isDigged() && !selected && selectionModel.isPartiallySelected(path))
            selected = !selected;
        selectionModel.removeTreeSelectionListener(this);

        try{
            if(selected){
                if(changeListener!=null)
                    changeListener.vetoableChange(new PropertyChangeEvent(this, "uncheckSelection", path, null));
                selectionModel.removeSelectionPath(path);
            }else{
                if(changeListener!=null)
                    changeListener.vetoableChange(new PropertyChangeEvent(this, "checkSelection", null, path));
                selectionModel.addSelectionPath(path);
            }
        }catch(PropertyVetoException ex){
            // ignore
        }finally{
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
                label.setText("Selection: "+ (text.isEmpty() ? text : text.substring(0, text.length()-2)));
            }
        }
    }

    JLabel label = new JLabel("Selection:");
    public void mouseClicked(MouseEvent me){
        TreePath path = tree.getPathForLocation(me.getX(), me.getY());
        if(path==null)
            return;
        if(checkBoxCustomer!=null && !checkBoxCustomer.showCheckBox(path))
            return;
        if(me.getX()>tree.getPathBounds(path).x+hotspot)
            return;

        toggleSelection(path);
    }

    public CheckTreeSelectionModel getSelectionModel(){
        return selectionModel;
    }

    public void valueChanged(TreeSelectionEvent e){
        tree.treeDidChange();
    }

    /*-----------------------------[ ActionListener ]------------------------------*/

    public void actionPerformed(ActionEvent e){
        toggleSelection(tree.getSelectionPath());
    }
}