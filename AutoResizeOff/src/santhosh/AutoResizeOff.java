package santhosh;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import javax.swing.border.Border;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 */
public class AutoResizeOff{
    static class MyTable extends JTable{
        public MyTable(TableModel dm){
            super(dm);
        }
        public boolean getScrollableTracksViewportHeight() {
            return getPreferredSize().height < getParent().getHeight();
        }

        private JPanel headerPanel = new JPanel(new BorderLayout());
        private JLabel lb = new JLabel(){
            public Border getBorder(){
                return UIManager.getBorder("TableHeader.cellBorder");
            }
        };

        MouseListener viewPortMouseListener = new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                repostEvent(e);
            }
            public void mousePressed(MouseEvent e){
                repostEvent(e);
            }

            public void mouseReleased(MouseEvent e){
                repostEvent(e);
            }
            public void mouseEntered(MouseEvent e){
                repostEvent(e);
            }
            public void mouseExited(MouseEvent e){
                repostEvent(e);
            }
            private void repostEvent(MouseEvent e) {
                MouseEvent e2 = SwingUtilities.convertMouseEvent(e.getComponent(), e,
                        MyTable.this);
                dispatchEvent(e2);
            }
        };

        protected void configureEnclosingScrollPane() {
            if(getAutoResizeMode()!=AUTO_RESIZE_OFF){
                super.configureEnclosingScrollPane();
                return;
            }
            Container p = getParent();
            if (p instanceof JViewport) {
                Container gp = p.getParent();
                if (gp instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane)gp;
                    // Make certain we are the viewPort's view and not, for
                    // example, the rowHeaderView of the scrollPane -
                    // an implementor of fixed columns might do this.
                    JViewport viewport = scrollPane.getViewport();
                    if (viewport == null || viewport.getView() != this) {
                        return;
                    }

                    headerPanel.removeAll();
                    headerPanel.add(getTableHeader(), BorderLayout.WEST);
                    headerPanel.add(lb, BorderLayout.CENTER);
                    scrollPane.setColumnHeaderView(headerPanel);
                    //  scrollPane.getViewport().setBackingStoreEnabled(true);
                    Border border = scrollPane.getBorder();
                    if (border == null || border instanceof UIResource) {
                        scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));       //NOI18N
                    }
                    viewport.setBackground(getBackground());
                    viewport.addMouseListener(viewPortMouseListener);
                }
            }
        }

        protected void unconfigureEnclosingScrollPane() {
            Container p = getParent();
            if (p instanceof JViewport) {
                Container gp = p.getParent();
                if (gp instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane)gp;
                    // Make certain we are the viewPort's view and not, for
                    // example, the rowHeaderView of the scrollPane -
                    // an implementor of fixed columns might do this.
                    JViewport viewport = scrollPane.getViewport();
                    if (viewport == null || viewport.getView() != this) {
                        return;
                    }
                    scrollPane.setColumnHeaderView(null);
                    viewport.removeMouseListener(viewPortMouseListener);
                }
            }
        }
    }

    static MouseListener mouseListener = new MouseAdapter(){
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger()){
                JPopupMenu popup = new JPopupMenu();
                popup.add("action1");
                popup.add("action2");
                popup.add("action3");
                popup.add("action3");
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    };

    private static JScrollPane createScroll(JComponent comp){
        JScrollPane scroll = new JScrollPane(comp);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignore){
        }

        TableModel model = new DefaultTableModel(new Object[][]{
            {"[xxxxxxxxxxxxxxxxxxxxxxxxxxxxx]", "[yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy]"},
            {"[xxxxxxxxxxxxxx]", "[yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy]"},
            {"[xxxxxxxxxx]", "[yyyyyyyyyy]"}
        },
                new String[]{"col1", "col2"});

        JTable table = new JTable(model);
        JTable mytable = new MyTable(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mytable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setShowGrid(false);
        mytable.setShowGrid(false);

        table.addMouseListener(mouseListener);
        mytable.addMouseListener(mouseListener);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createScroll(table), createScroll(mytable));
        split.setResizeWeight(0.5d);
        JFrame frame = new JFrame("AutoResizeOff - santhosh@in.fiorano.com");
        frame.getContentPane().add(split);
        frame.setSize(600, 300);
        frame.setVisible(true);
    }
}
