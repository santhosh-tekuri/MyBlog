package santhosh;

import com.jidesoft.combobox.DateComboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Date;
import java.awt.*;

public class JideComboBug{
    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        String colNames[] = {
            "name", "value"
        };
        Object data[][] = {
            { "date1", new Date()},
            { "date2", new Date()},
            { "date3", new Date()},
            { "date4", new Date()},
            { "date5", new Date()},
            { "date6", new Date()},
        };

        DefaultTableModel model = new DefaultTableModel(data, colNames){
            public Class getColumnClass(int col){
                return col==1 ? Date.class : String.class;
            }

            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setDefaultRenderer(Date.class, new TableCellRenderer(){
            TableCellRenderer renderer = new DefaultTableCellRenderer();
            DateComboBox combo = new DateComboBox();
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                if(isSelected){
                    System.err.println("xxx");
                    combo.setDate((Date)value);
                    return combo;
                }else
                    return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}
