package santhosh;

import javax.swing.*;
import java.awt.dnd.*;
import java.awt.*;

/**
 * @author santhosh kumar T
 */
public class ListDropListener implements DropTargetListener{
    private Component oldGlassPane;
    private Point from, to;

    // glasspane on which visual clues are drawn
    JPanel glassPane = new JPanel(){
        public void paint(Graphics g) {
            g.setColor(Color.red);
            if(from==null || to==null)
                return;
            int x1 = from.x;
            int x2 = to.x;
            int y1 = from.y;

            // line
            g.drawLine(x1+2, y1, x2-2, y1);
            g.drawLine(x1+2, y1+1, x2-2, y1+1);

            // right
            g.drawLine(x1, y1-2, x1, y1+3);
            g.drawLine(x1+1, y1-1, x1+1, y1+2);

            // left
            g.drawLine(x2, y1-2, x2, y1+3);
            g.drawLine(x2-1, y1-1, x2-1, y1+2);
        }
    };

    // size of hotspot used to find
    // the whether user wants to insert element
    private int hotspot = 5;

    // dropindex - subclasses can access this in to accept/reject drop
    protected int listIndex = -1;

    // null means replace element at listIndex
    // true means insert element before listIndex
    // false means insert element after listIndex
    // subclasses can access this in drop
    protected Boolean before = null;

    private void updateLine(JList list, Point pt){
        listIndex = pt!=null ? list.locationToIndex(pt) : -1;
        if(listIndex==-1){
            from=to=null;
            before = null;
            list.clearSelection();
        }else{
            Rectangle bounds = list.getCellBounds(listIndex, listIndex);
            if(pt.y <= bounds.y+hotspot){
                from = bounds.getLocation();
                to = new Point(list.getWidth(), from.y);
                before = Boolean.TRUE;
            }else if(pt.y>=bounds.y+bounds.height-hotspot){
                from = new Point(bounds.x, bounds.y+bounds.height);
                to = new Point(list.getWidth(), from.y);
                before = Boolean.FALSE;
            }else{
                from = to = null;
                before = null;
            }
            if(from!=null && to!=null){
                from = SwingUtilities.convertPoint(list, from, glassPane);
                to = SwingUtilities.convertPoint(list, to, glassPane);
                list.clearSelection();
            }else
                list.setSelectedIndex(listIndex);
        }
        glassPane.getRootPane().repaint();
    }

    public void dragEnter(DropTargetDragEvent dtde){
        JList list = (JList)dtde.getDropTargetContext().getComponent();
        Point location = dtde.getLocation();

        JRootPane rootPane = list.getRootPane();
        oldGlassPane = rootPane.getGlassPane();
        rootPane.setGlassPane(glassPane);
        glassPane.setOpaque(false);
        glassPane.setVisible(true);

        updateLine(list, location);
    }

    public void dragOver(DropTargetDragEvent dtde){
        JList list = (JList)dtde.getDropTargetContext().getComponent();
        Point location = dtde.getLocation();
        updateLine(list, location);
    }

    public void dropActionChanged(DropTargetDragEvent dtde){
    }

    private void resetGlassPane(DropTargetEvent dte){
        JList list = (JList)dte.getDropTargetContext().getComponent();

        JRootPane rootPane = list.getRootPane();
        rootPane.setGlassPane(oldGlassPane);
        oldGlassPane.setVisible(false);
        rootPane.repaint();
    }

    public void dragExit(DropTargetEvent dte){
        resetGlassPane(dte);
    }

    public void drop(DropTargetDropEvent dtde){
        resetGlassPane(dtde);
    }
}