package santhosh;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.EventObject;

public class ListInTable{
    public static final String WEEK_DAYS[] = {"sunday", "monday", "tuesday", "wednessday", "thursday", "friday", "saturday"};

    private static JScrollPane createScroll(Component comp){
        JScrollPane scroll = new JScrollPane(comp);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        String columnNames[] = {"Scheduler Task", "Days on which it runs"};
        Object rowData[][] = {
            { "AntiVirus Scan", new int[]{0, 1, 2} },
            { "Disk Fragmentation", new int[]{3, 5, 6} },
        };

        final JTable table = new JTable(rowData, columnNames);
        TableColumn column = table.getColumn(columnNames[1]);
        ListTableCellRenderer renderer = new ListTableCellRenderer();
        column.setCellRenderer(renderer);
        column.setCellEditor(new ListTableCellEditor(renderer));
        table.setRowHeight(renderer.getPreferredSize().height);

        final JTextArea area = new JTextArea(10, 50);
        area.setEditable(false);
        table.getModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e){
                int row = e.getFirstRow();
                int col = e.getColumn();
                Object value = table.getValueAt(row, col);
                if(value instanceof int[]){
                    int arr[] = (int[])value;
                    String buf = "";
                    for(int i = 0; i<arr.length; i++){
                        if(i!=0)
                            buf+=',';
                        buf += " "+arr[i];
                    }
                    value = buf;
                }
                area.append("["+row+','+col+"] ---> "+value+"\n");
            }
        });

        JFrame frame = new JFrame("Live Components in Table - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JSplitPane comp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createScroll(table), createScroll(area));
        comp.setResizeWeight(0.7d);
        frame.getContentPane().add(comp);
        frame.setSize(500, 500);
        frame.show();
    }
}

class ListTableCellRenderer extends JScrollPane implements TableCellRenderer{
    JList list = new JList(ListInTable.WEEK_DAYS);

    public ListTableCellRenderer(){
        setViewportView(list);
        list.setVisibleRowCount(4);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        list.setSelectedIndices((int[])value);
        return this;
    }
}

class ListTableCellEditor extends DefaultCellEditor{
    JList list;

    public ListTableCellEditor(JScrollPane scroll){
        super(new JCheckBox()); // workaround
        this.list = (JList)scroll.getViewport().getView();
        editorComponent = scroll;

        delegate = new EditorDelegate(){
            public void setValue(Object value){
                list.setSelectedIndices((int[])value);
            }
            public Object getCellEditorValue(){
                return list.getSelectedIndices();
            }
            public boolean isCellEditable(EventObject anEvent){
                return true;
            }
        };
        list.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e){
                delegate.itemStateChanged(null);
            }
        });
    }
}
