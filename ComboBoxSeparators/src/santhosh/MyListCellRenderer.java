package santhosh;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MyListCellRenderer extends JPanel implements ListCellRenderer{
    private JSeparator separator = new JSeparator();
    private ListCellRenderer delegate;
    private int showSeparatorsAfter[];

    // showSeparatorsAfter must be sorted array
    public MyListCellRenderer(ListCellRenderer delegate, int showSeparatorsAfter[]){
        super(new BorderLayout());
        this.delegate = delegate;
        this.showSeparatorsAfter = showSeparatorsAfter;
//        separator.setPreferredSize(new Dimension(10, 10));
//        separator.setBorder(BorderFactory.createLineBorder(Color.red));
        setOpaque(false);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component renderer = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if(Arrays.binarySearch(showSeparatorsAfter, index)==index){
            removeAll();
            add(renderer, BorderLayout.CENTER);
            add(separator, BorderLayout.SOUTH);
            return this;
        }
        return renderer;
    }
}