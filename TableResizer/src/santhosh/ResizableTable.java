package santhosh;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableModel;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ResizableTable extends JTable{
    protected MouseInputAdapter rowResizer, columnResizer = null;

    public ResizableTable(TableModel dm){
        super(dm);
    }

    // turn resizing on/of
    public void setResizable(boolean row, boolean column){
        if(row){
            if(rowResizer==null)
                rowResizer = new TableRowResizer(this);
        }else if(rowResizer!=null){
            removeMouseListener(rowResizer);
            removeMouseMotionListener(rowResizer);
            rowResizer = null;
        }
        if(column){
            if(columnResizer==null)
                columnResizer = new TableColumnResizer(this);
        }else if(columnResizer!=null){
            removeMouseListener(columnResizer);
            removeMouseMotionListener(columnResizer);
            columnResizer = null;
        }
    }

    // mouse press intended for resize shouldn't change row/col/cell celection
    public void changeSelection(int row, int column, boolean toggle, boolean extend) {
        if(getCursor()==TableColumnResizer.resizeCursor || getCursor()==TableRowResizer.resizeCursor)
            return;
        super.changeSelection(row, column, toggle, extend);
    }
}
