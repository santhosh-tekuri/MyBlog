package santhosh;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class HighlightTreeDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignore){}

        final HighlighterTree tree = new HighlighterTree();
        for(int i=0; i<tree.getRowCount(); i++)
            tree.expandRow(i);
        tree.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent me){
                if(SwingUtilities.isRightMouseButton(me)){
                    TreePath path = tree.getPathForLocation(me.getX(), me.getY());
                    if(path!=null){
                        tree.setSelectionPath(path);
                        JPopupMenu popup = new JPopupMenu();
                        popup.add(new HighlightAction(tree, path));
                        popup.show(tree, me.getX(), me.getY());
                    }
                }
            }
        });

        tree.setCellRenderer(new MyTreeCellRenderer());

        JFrame frame = new JFrame("Highlight a node's descendants in JTree - santhosh@in.fiornao.com");
        frame.getContentPane().add(new JScrollPane(tree));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setVisible(true);
    }

    static class HighlightAction extends AbstractAction{
        HighlighterTree tree;
        TreePath path;

        public HighlightAction(HighlighterTree tree, TreePath path){
            super("Highlight Descendants");
            this.tree = tree;
            this.path = path;
        }
        public void actionPerformed(ActionEvent e){
            tree.setHighlightPath(path);
        }
    }
}
