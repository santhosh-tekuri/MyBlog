package santhosh;


import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.util.EventObject;
import java.util.Arrays;
import java.awt.*;

public class ListInTable{
    public static final String week[] = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    public static void main(String[] args){
        String columnNames[] = {"String", "List"};
        Object rowData[][] = {
            { "column1", new String[]{"sun", "mon", "tue"} },
            { "column1", new String[]{"wed", "thu", "fri"} },
        };

        JTable table = new JTable(rowData, columnNames);
        JFrame frame = new JFrame("List In Table");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));

        TableColumn column = table.getColumn("List");
        ListTableCellRenderer renderer = new ListTableCellRenderer();
        column.setCellRenderer(renderer);
        column.setCellEditor(new ListTableCellEditor(renderer));
        table.setRowHeight(renderer.getPreferredSize().height-15);
        frame.pack();
        frame.show();
    }
}

class ListTableCellRenderer extends JScrollPane implements TableCellRenderer{
    JList list = new JList(ListInTable.week);

    public ListTableCellRenderer(){
        setViewportView(list);
        list.setVisibleRowCount(6);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        DefaultListModel model = new DefaultListModel();
        String str[] = (String[])value;
        for(int i = 0; i<str.length; i++)
            model.addElement(str[i]);
        for(int i = 0; i<ListInTable.week.length; i++){
            if(!Arrays.asList(str).contains(ListInTable.week[i]))
                model.addElement(ListInTable.week[i]);
        }
        list.setModel(model);
        list.addSelectionInterval(0, str.length-1);
        return this;
    }
}
class ListTableCellEditor extends DefaultCellEditor{
    JList list;

    public ListTableCellEditor(JScrollPane scroll){
        super(new JCheckBox());
        this.list = (JList)scroll.getViewport().getView();
        editorComponent = scroll;
        delegate = new EditorDelegate(){
            public void setValue(Object value){
                DefaultListModel model = new DefaultListModel();
                String str[] = (String[])value;
                for(int i = 0; i<str.length; i++)
                    model.addElement(str[i]);
                for(int i = 0; i<ListInTable.week.length; i++){
                    if(!Arrays.asList(str).contains(ListInTable.week[i]))
                        model.addElement(ListInTable.week[i]);
                }
                list.setModel(model);
                list.addSelectionInterval(0, str.length-1);
            }

            public Object getCellEditorValue(){
                return Arrays.asList(list.getSelectedValues()).toArray(new String[0]);
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
        setClickCountToStart(0);
    }
}
