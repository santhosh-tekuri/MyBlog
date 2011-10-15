package santhosh;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class DropDownUndoRedo{
    static class MyUndoManager extends UndoManager{
        public MyUndoManager(){
            undoAction.setEnabled(false);
            redoAction.setEnabled(false);
        }

        public synchronized void undo() throws CannotUndoException{
            super.undo();
            refreshActions();
        }

        public synchronized void redo() throws CannotRedoException{
            super.redo();
            refreshActions();
        }

        public void undoableEditHappened(UndoableEditEvent e){
            super.undoableEditHappened(e);
            refreshActions();
        }

        private void refreshActions(){
            undoAction.setEnabled(canUndo());
            redoAction.setEnabled(canRedo());
        }

        public Vector getUndoList() {
            UndoableEdit edit = super.editToBeUndone();
            Vector list = new Vector();
            int i = edits.indexOf(edit);
            while(i >= 0)
                list.add(((UndoableEdit)edits.get(i--)).getPresentationName());
            return list;
        }

        public Vector getRedoList() {
            UndoableEdit edit = super.editToBeRedone();
            Vector list = new Vector();
            int i = edits.indexOf(edit);
            while(i < edits.size())
                list.add(((UndoableEdit)edits.get(i++)).getPresentationName());
            return list;
        }

        Action undoAction = new AbstractAction("undo"){
            {
                putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(getValue(Action.NAME)+".gif")));
            }

            public void actionPerformed(ActionEvent e){
                undo();
            }
        };

        Action redoAction = new AbstractAction("redo"){
            {
                putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(getValue(Action.NAME)+".gif")));
            }

            public void actionPerformed(ActionEvent e){
                redo();
            }
        };

    }

    static class UndoRedoList extends JList{
        MyUndoManager undoManager;
        boolean undo;

        public UndoRedoList(final MyUndoManager undoManager, final boolean undo){
            super(undo ? undoManager.getUndoList() : undoManager.getRedoList());
            this.undoManager = undoManager;
            this.undo = undo;
            setVisibleRowCount(5);

            MouseInputAdapter adapter = new MouseInputAdapter(){
                public void mouseMoved(MouseEvent me) {
                    JPopupMenu popup = (JPopupMenu)SwingUtilities.getAncestorOfClass(JPopupMenu.class, UndoRedoList.this);
                    int item = locationToIndex(me.getPoint());
                    if(item != -1) {
                        setSelectionInterval(0, item);
                        JLabel label = (JLabel)((Container)popup.getComponent(0)).getComponent(1);
                        label.setText((undo ? "Undo" : "Redo") + " " + (item + 1) + " action(s)");
                    }
                }
                public void mousePressed(MouseEvent me){
                    JPopupMenu popup = (JPopupMenu)SwingUtilities.getAncestorOfClass(JPopupMenu.class, UndoRedoList.this);
                    popup.setVisible(false);
                    for(int i = getMaxSelectionIndex(); i>= 0; i--){
                        if(undo)
                            undoManager.undo();
                        else
                            undoManager.redo();
                    }
                }
            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        }
    }

    static class UndoRedoDropDown extends DropDownButton{
        MyUndoManager undoManager;
        boolean undo;

        public UndoRedoDropDown(MyUndoManager undoManager, boolean undo){
            this.undoManager = undoManager;
            this.undo = undo;
            putClientProperty("hideActionText", Boolean.TRUE);
            setAction(undo ? undoManager.undoAction: undoManager.redoAction);
        }

        protected JPopupMenu getPopupMenu(){
            JPopupMenu popup = new JPopupMenu();
            JPanel panel = new JPanel(new BorderLayout());
            JScrollPane scroll = new JScrollPane(new UndoRedoList(undoManager, undo));
            scroll.setBorder(BorderFactory.createEmptyBorder());
            panel.add(scroll, BorderLayout.CENTER);
            panel.add(new JLabel("                                   ", JLabel.CENTER), BorderLayout.SOUTH);
            popup.add(panel);
            popup.setBorder(BorderFactory.createLineBorder(Color.black));
            return popup;
        }
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Extreme Use of DropDownButton - santhosh@in.fiorano.com");

        JTextArea textArea = new JTextArea("Did you like this blog entry ?? plz send ur comments \n - santhosh \n   santhosh@in.fiorano.com\n\n");
        MyUndoManager undoManager = new MyUndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.add(DropDownButton.createAction("cut")).setAlignmentX(0.5f);
        toolbar.add(DropDownButton.createAction("copy")).setAlignmentX(0.5f);
        toolbar.add(DropDownButton.createAction("delete")).setAlignmentX(0.5f);
        toolbar.addSeparator();
        new UndoRedoDropDown(undoManager, true).addToToolBar(toolbar);
        new UndoRedoDropDown(undoManager, false).addToToolBar(toolbar);

        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}