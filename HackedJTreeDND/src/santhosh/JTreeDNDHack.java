package santhosh;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.event.*;
import java.awt.dnd.DnDConstants;

public class JTreeDNDHack{
    public static void applyDNDHack(JTree tree){
        MouseListener dragListener = null;

        // the default dnd implemntation requires to first select node and then drag
        try{
            Class clazz = Class.forName("javax.swing.plaf.basic.BasicDragGestureRecognizer");
            MouseListener[] mouseListeners = tree.getMouseListeners();
            for(int i = 0; i<mouseListeners.length; i++){
                if(clazz.isAssignableFrom(mouseListeners[i].getClass())){
                    dragListener = mouseListeners[i];
                    break;
                }
            }

            if(dragListener!=null){
                tree.removeMouseListener(dragListener);
                tree.removeMouseMotionListener((MouseMotionListener)dragListener);
                tree.addMouseListener(dragListener);
                tree.addMouseMotionListener((MouseMotionListener)dragListener);
            }
        } catch(ClassNotFoundException e){
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    static class MyTree extends JTree{
        private int mouseButtonDown = MouseEvent.NOBUTTON;
        private long mouseButtonDownSince = 0;

        public MyTree(){
            initCustomDragGestureRecognizer();
        }

        private void initCustomDragGestureRecognizer(){
            addMouseListener(new MouseAdapter(){
                public void mousePressed(MouseEvent e){
                    mouseButtonDownSince = System.currentTimeMillis();
                    mouseButtonDown = e.getButton();
                }

                public void mouseReleased(MouseEvent e){
                    mouseButtonDown = MouseEvent.NOBUTTON;
                }
            });
            addMouseMotionListener(new MouseMotionAdapter(){
                public void mouseDragged(MouseEvent e){
                    System.err.println("1");
//                    if(mouseButtonDown!=MouseEvent.NOBUTTON && (System.currentTimeMillis()-mouseButtonDownSince)>1){
//                        System.err.println("hhh");
                        if(getPathForLocation(e.getX(), e.getY())!=null)
                            getTransferHandler().exportAsDrag(MyTree.this, e, DnDConstants.ACTION_COPY);
//                    } else
//                        mouseButtonDown = MouseEvent.NOBUTTON;
                }
            });
        }
    }

    private static JScrollPane createScroll(JComponent comp, String name){
        JScrollPane scroll = new JScrollPane(comp);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setColumnHeaderView(new JLabel("  "+name));
        return scroll;
    }

    public static void main(String[] args){
		try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ignore){}

        JTree hackedTree = new MyTree();
        applyDNDHack(hackedTree);
        hackedTree.setDragEnabled(true);
        hackedTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JTree normalTree = new JTree();
        normalTree.setDragEnabled(true);
        normalTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createScroll(hackedTree, "Hacked JTree"), createScroll(normalTree, "Normal JTree"));
        split.setResizeWeight(0.5d);
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split, createScroll(new JTextArea(), "Drop TreeNodes here"));
        split.setResizeWeight(0.7);

        JFrame frame = new JFrame("JTree DND Hack - santhosh@in.fiorano.com");
        frame.getContentPane().add(split);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}