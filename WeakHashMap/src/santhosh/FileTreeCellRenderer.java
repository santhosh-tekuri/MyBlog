package santhosh;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class FileTreeCellRenderer extends DefaultTreeCellRenderer{
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel
                                                  , boolean expanded, boolean leaf, int row, boolean hasFocus){
        FileSystemView fs = (FileSystemView)tree.getModel().getRoot();
        File file = (File)value;
        Icon icon = fs.getSystemIcon(file);
        value = fs.getSystemDisplayName(file);
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        setIcon(icon);
        return this;
    }
}
