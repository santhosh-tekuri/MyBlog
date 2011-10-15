package santhosh;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableCellEditor;
import java.util.Vector;
import java.util.EventObject;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.applet.Applet;

public class MyTable extends JTable{

    /*---------------------------------[ Constructors ]---------------------------------*/

    public MyTable(){
    }

    public MyTable(TableModel dm){
        super(dm);
    }

    public MyTable(TableModel dm, TableColumnModel cm){
        super(dm, cm);
    }

    public MyTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm){
        super(dm, cm, sm);
    }

    public MyTable(int numRows, int numColumns){
        super(numRows, numColumns);
    }

    public MyTable(Vector rowData, Vector columnNames){
        super(rowData, columnNames);
    }

    public MyTable(final Object[][] rowData, final Object[] columnNames){
        super(rowData, columnNames);
    }

    public boolean getScrollableTracksViewportHeight(){
        if(getParent() instanceof JViewport)
            return getParent().getHeight()>getPreferredSize().height;
        else
            return false;
    }

    /*-------------------------------------------------[ Cell Editing ]---------------------------------------------------*/

    public boolean editCellAt(int row, int column, EventObject e){
        if(cellEditor!=null && !cellEditor.stopCellEditing()){
            return false;
        }

        if(row<0 || row>=getRowCount() ||
                column<0 || column>=getColumnCount()){
            return false;
        }

        if(!isCellEditable(row, column))
            return false;

        if(editorRemover==null){
            KeyboardFocusManager fm =
                    KeyboardFocusManager.getCurrentKeyboardFocusManager();
            editorRemover = new CellEditorRemover(fm);
            fm.addPropertyChangeListener("permanentFocusOwner", editorRemover);
        }

        TableCellEditor editor = getCellEditor(row, column);
        if(editor!=null && editor.isCellEditable(e)){
            editorComp = prepareEditor(editor, row, column);
            if(editorComp==null){
                removeEditor();
                return false;
            }

            if(editorComp instanceof AutoResizable)
                ((AutoResizable)editorComp).doResize(this, row, column);
            else
                editorComp.setBounds(getCellRect(row, column, false));

            add(editorComp);
            editorComp.validate();
            editorComp.repaint();

            setCellEditor(editor);
            setEditingRow(row);
            setEditingColumn(column);
            editor.addCellEditorListener(this);

            return true;
        }
        return false;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(isEditing()){
            Component component = getEditorComponent();
            component.repaint();
        }
    }

    /*-------------------------------------------------[ Cleanup ]---------------------------------------------------*/

    public void removeNotify(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().
                removePropertyChangeListener("permanentFocusOwner", editorRemover);
        editorRemover = null;
        super.removeNotify();
    }

    public void removeEditor(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().
                removePropertyChangeListener("permanentFocusOwner", editorRemover);

        editorRemover = null;
        TableCellEditor editor = getCellEditor();
        if(editor!=null){
            editor.removeCellEditorListener(this);
            Rectangle cellRect = getCellRect(editingRow, editingColumn, false);
            if(editorComp!=null){
                cellRect = cellRect.union(editorComp.getBounds());
                remove(editorComp);
            }

            setCellEditor(null);
            setEditingColumn(-1);
            setEditingRow(-1);
            editorComp = null;

            repaint(cellRect);
        }
    }
    /*-------------------------------------------------[ Editor Remover ]---------------------------------------------------*/

    private PropertyChangeListener editorRemover = null;

    class CellEditorRemover implements PropertyChangeListener{
        KeyboardFocusManager focusManager;

        public CellEditorRemover(KeyboardFocusManager fm) {
            this.focusManager = fm;
        }

        public void propertyChange(PropertyChangeEvent ev) {
            if (!isEditing() || getClientProperty("terminateEditOnFocusLost") != Boolean.TRUE) {
                return;
            }

            Component c = focusManager.getPermanentFocusOwner();
            while (c != null) {
                if (c == MyTable.this) {
                    // focus remains inside the table
                    return;
                } else if ((c instanceof Window) ||
                           (c instanceof Applet && c.getParent() == null)) {
                    if (c == SwingUtilities.getRoot(MyTable.this)) {
                        if (!getCellEditor().stopCellEditing()) {
                            getCellEditor().cancelCellEditing();
                        }
                    }
                    break;
                }
                c = c.getParent();
            }
        }
    }

    public static interface AutoResizable{
        public void doResize(JTable table, int row, int col);
    }
}