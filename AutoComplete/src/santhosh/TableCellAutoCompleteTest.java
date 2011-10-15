package santhosh;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class TableCellAutoCompleteTest{
    public static void main(String[] args){
        JDialog dlg = new JDialog();
        final JTextField tf = new JTextField("abcd");
        dlg.getContentPane().setLayout(new FlowLayout());
        tf.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
            }
            public void insertUpdate(DocumentEvent e){
                tf.getParent().doLayout();
            }

            public void removeUpdate(DocumentEvent e){
                tf.getParent().doLayout();
            }
        });
        dlg.getContentPane().add(tf);
        dlg.setSize(200, 100);
        dlg.show();
//        JTable table = new JTable(5, 5);
//        DefaultCellEditor editor = (DefaultCellEditor)table.getDefaultEditor(Object.class);
//        new FileAutoCompleter((JTextComponent)editor.getComponent());
//        System.err.println("editor: "+editor);
//        table.setValueAt("d:\\", 0, 1);
//        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    }
}
