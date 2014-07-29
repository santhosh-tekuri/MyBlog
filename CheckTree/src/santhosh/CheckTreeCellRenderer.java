package santhosh;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;

public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer{
    private CheckTreeSelectionModel selectionModel;
    private TreeCellRenderer delegate;
    private boolean showRootNodeCheckBox;
    private TristateCheckBox checkBox = new TristateCheckBox("");
    protected CheckTreeManager.CheckBoxCustomizer checkBoxCustomer;
    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel, boolean showRootNodeCheckBox){
        this.delegate = delegate;
        this.selectionModel = selectionModel;
        this.showRootNodeCheckBox = showRootNodeCheckBox;
        setLayout(new BorderLayout());
        setOpaque(false);
        checkBox.setOpaque(false);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        if(!showRootNodeCheckBox && tree.getModel().getRoot()==value)
            return renderer;

        TreePath path = tree.getPathForRow(row);
        if(path!=null){
            if(checkBoxCustomer!=null && !checkBoxCustomer.showCheckBox(path))
                return renderer;
            if(selectionModel.isPathSelected(path, selectionModel.isDigged()))
                checkBox.getTristateModel().setState(TristateState.SELECTED);
            else
                checkBox.getTristateModel().setState(selectionModel.isDigged() && selectionModel.isPartiallySelected(path) ? TristateState.INDETERMINATE : TristateState.DESELECTED);
        }
        removeAll();
        add(checkBox, BorderLayout.WEST);
        add(renderer, BorderLayout.CENTER);
        return this;
    }
}
