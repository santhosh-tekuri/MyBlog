package santhosh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class MyTable extends JTable{

    // JTree has convertValueToText(), but why not JTable ???
    public String convertValueToText(Object value, int row, int column){
        if(value != null) {
            String sValue = value.toString();
            if (sValue != null)
                return sValue;
        }
        return "";
    }

    public String getToolTipText(MouseEvent event) {
        String tip = null;
        Point p = event.getPoint();

        // Locate the renderer under the event location
        int hitColumnIndex = columnAtPoint(p);
        int hitRowIndex = rowAtPoint(p);

        if ((hitColumnIndex != -1) && (hitRowIndex != -1)) {
            TableCellRenderer renderer = getCellRenderer(hitRowIndex, hitColumnIndex);
            Component component = prepareRenderer(renderer, hitRowIndex, hitColumnIndex);

            // Now have to see if the component is a JComponent before
            // getting the tip
            if (component instanceof JComponent) {
                // Convert the event to the renderer's coordinate system
                Rectangle cellRect = getCellRect(hitRowIndex, hitColumnIndex, false);
                if(cellRect.width>=component.getPreferredSize().width)
                    return null;
                p.translate(-cellRect.x, -cellRect.y);
                MouseEvent newEvent = new MouseEvent(component, event.getID(),
                                          event.getWhen(), event.getModifiers(),
                                          p.x, p.y, event.getClickCount(),
                                          event.isPopupTrigger());

                tip = ((JComponent)component).getToolTipText(newEvent);
            }
        }

        // No tip from the renderer, see whether any tooltip is set on JTable
        if (tip == null)
            tip = getToolTipText();

        // calculate tooltip from cell value
        if(tip==null){
            Object value = getValueAt(hitRowIndex, hitColumnIndex);
            tip = convertValueToText(value, hitRowIndex, hitColumnIndex);
            if(tip.length()==0)
                tip = null; // don't show empty tooltips
        }

        return tip;
    }

    // makes the tooltip's location to match table cell location
    // also avoids showing empty tooltips
    public Point getToolTipLocation(MouseEvent event){
        int row = rowAtPoint( event.getPoint() );
        if(row==-1)
            return null;
        int col = columnAtPoint( event.getPoint() );
        if(col==-1)
            return null;

        // to avoid empty tooltips - return null location
        boolean hasTooltip = getToolTipText()==null
                ? getToolTipText(event)!=null
                : true;

        return hasTooltip
                ? getCellRect(row, col, false).getLocation()
                : null;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JTable table = new MyTable();
        String columns[] = new String[]{
            "Column1",
            "Column2",
            "Column3",
            "Column4",
        };

        String data[][] = new String[][]{
            { "test", "this is simple test", "This is really very long sentence!"},
            { "test", "this is simple test", "This is really very long sentence!"},
            { "test", "this is simple test", "This is really very long sentence!"},
        };

        table.setModel(new DefaultTableModel(data, columns));

        JFrame frame = new JFrame("Partially Visible TableCell - santhosh@in.fiorano.com");
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
