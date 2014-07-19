package santhosh;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;

// @author Santhosh Kumr T - santhosh@in.fiorano.com
public class CheckTreeTableManager extends CheckTreeManager{

    public CheckTreeTableManager(JXTreeTable treeTable, boolean dig, boolean showRootNodeCheckBox){
        super(getTree(treeTable)), dig, showRootNodeCheckBox);
        treeTable.addMouseListener(treeTableMouseListener);
    }

    private static JTree getTree(JXTreeTable treeTable){
        try{
            Field field = JXTreeTable.class.getDeclaredField("renderer");
            field.setAccessible(true);
            return (JTree)field.get(treeTable);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private MouseListener treeTableMouseListener = new MouseAdapter(){
        public void mouseClicked(MouseEvent me){
            JXTreeTable treeTable = (JXTreeTable)me.getComponent();
            if(me.getModifiers()==0 ||
                    me.getModifiers()==InputEvent.BUTTON1_MASK){
                final int count = treeTable.getColumnCount();
                for(int i = count-1; i>= 0; i--){
                    if(treeTable.isHierarchical(i)){

                        int savedHeight = tree.getRowHeight();
                        tree.setRowHeight(treeTable.getRowHeight());
                        MouseEvent pressed = new MouseEvent
                                (tree,
                                        me.getID(),
                                        me.getWhen(),
                                        me.getModifiers(),
                                        me.getX()-treeTable.getCellRect(0, i, false).x,
                                        me.getY(),
                                        me.getClickCount(),
                                        me.isPopupTrigger());
                        tree.dispatchEvent(pressed);
                        // For Mac OS X, we need to dispatch a MOUSE_RELEASED as well
                        MouseEvent released = new MouseEvent
                                (tree,
                                        java.awt.event.MouseEvent.MOUSE_RELEASED,
                                        pressed.getWhen(),
                                        pressed.getModifiers(),
                                        pressed.getX(),
                                        pressed.getY(),
                                        pressed.getClickCount(),
                                        pressed.isPopupTrigger());
                        tree.dispatchEvent(released);
                        tree.setRowHeight(savedHeight);
                        break;
                    }
                }
            }
        }
    };
}
