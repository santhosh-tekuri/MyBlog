package santhosh;

import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class OverlayListener extends MouseInputAdapter implements TreeSelectionListener{
    JTree tree;
    Component oldGlassPane;
    TreePath path;
    int row;
    Rectangle bounds;

    public OverlayListener(JTree tree){
        this.tree = tree;
        tree.addMouseListener(this);
        tree.addMouseMotionListener(this);
    }

    JComponent c = new JComponent(){
        public void paint(Graphics g){
            boolean selected = tree.isRowSelected(row);
            Component renderer = tree.getCellRenderer().getTreeCellRendererComponent(tree, path.getLastPathComponent(),
                    tree.isRowSelected(row), tree.isExpanded(row), tree.getModel().isLeaf(path.getLastPathComponent()), row,
                    selected);
            c.setFont(tree.getFont());
            Rectangle paintBounds = SwingUtilities.convertRectangle(tree, bounds, this);
            SwingUtilities.paintComponent(g, renderer, this, paintBounds);
            if(selected)
                return;

            g.setColor(Color.blue);
            ((Graphics2D)g).draw(paintBounds);
        }
    };

    public void mouseExited(MouseEvent e){
        resetGlassPane();
    }

    private void resetGlassPane(){
        if(oldGlassPane!=null){
            c.setVisible(false);
            tree.getRootPane().setGlassPane(oldGlassPane);
            oldGlassPane = null;
            tree.removeTreeSelectionListener(this);
        }
    }

    public void mouseMoved(MouseEvent me){
        path = tree.getPathForLocation(me.getX(), me.getY());
        if(path==null){
            resetGlassPane();
            return;
        }
        row = tree.getRowForPath(path);
        bounds = tree.getPathBounds(path);
        if(!tree.getVisibleRect().contains(bounds)){
            if(oldGlassPane==null){
                oldGlassPane = tree.getRootPane().getGlassPane();
                c.setOpaque(false);
                tree.getRootPane().setGlassPane(c);
                c.setVisible(true);
                tree.addTreeSelectionListener(this);
            }else
                tree.getRootPane().repaint();
        }else{
            resetGlassPane();
        }
    }


    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JTree tree = new JTree();
        new OverlayListener(tree);
        JSplitPane split = new JSplitPane();
        split.setLeftComponent(new JScrollPane(tree));
        JFrame frame = new JFrame("Partially Visible TreeNodes - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(split);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void valueChanged(TreeSelectionEvent e){
        tree.getRootPane().repaint();
    }
}
