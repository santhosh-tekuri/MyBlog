package santhosh;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * @author Santhosh Kumar T
 */
public class SummaryTableSortingDemo{
    public static void main(String[] args) {
        Integer data[][] = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {15, 18, 21, 24},    
        };
        JTable table = new JTable(data, new String[]{"col1", "col2", "col3", "col4"});
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(row==table.getRowCount()-1)
                    comp.setBackground(Color.YELLOW);
                else
                    comp.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                return comp;
            }
        });
        table.setRowSorter(new SummaryTableSorter<TableModel>(table.getModel()));
        JFrame frame = new JFrame("Sorting Table with Summary Row - santhosh.tekuri@gmail.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));
        frame.setSize(250, 250);
        frame.setVisible(true);
    }
}
