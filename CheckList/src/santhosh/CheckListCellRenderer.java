package santhosh;

import javax.swing.*;
import java.awt.*;

public class CheckListCellRenderer extends JPanel implements ListCellRenderer{
    private ListCellRenderer delegate;
    private ListSelectionModel selectionModel;
    private JCheckBox checkBox = new JCheckBox();

    public CheckListCellRenderer(ListCellRenderer renderer, ListSelectionModel selectionModel){
        this.delegate = renderer;
        this.selectionModel = selectionModel;
        setLayout(new BorderLayout());
        setOpaque(false);
        checkBox.setOpaque(false);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component renderer = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        checkBox.setSelected(selectionModel.isSelectedIndex(index));
        removeAll();
        add(checkBox, BorderLayout.WEST);
        add(renderer, BorderLayout.CENTER);
        return this;
    }
}