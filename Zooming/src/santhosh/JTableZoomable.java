package santhosh;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;

public class JTableZoomable extends ComponentZoomable{
    public JTableZoomable(Component comp) {
        super(comp);
    }

    public void zoom(float factor) {
        super.zoom(factor);
        JTable table = (JTable) comp;
        int rowHeight = table.getRowHeight();
        System.out.println("rowHeight = " + rowHeight);
        int scaledRowHeight = apply(rowHeight, factor);
        if(scaledRowHeight>=1){
            System.out.println("scaledRowHeight = " + scaledRowHeight);
            table.setRowHeight(scaledRowHeight);
        }

        JTableHeader tableHeader = table.getTableHeader();
        if(tableHeader!=null){
            new ComponentZoomable(tableHeader).zoom(factor);
            Enumeration enumer = table.getColumnModel().getColumns();
            while (enumer.hasMoreElements()) {
                TableColumn col = (TableColumn) enumer.nextElement();
                int width = col.getWidth();
                int scaledWidth = (int) (width + (width * factor));
                if(scaledWidth>=1){
                    tableHeader.setResizingColumn(col);
                    col.setWidth(scaledWidth);
                }
            }
        }
    }
}