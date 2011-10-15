package santhosh;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * MySwing: Advanced Swing Utilites
 * Copyright (C) 2005  Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * @author Santhosh Kumar T
 * @email santhosh@fiorano.com
 */
public class MultiLineTableCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new ResizableScrollPane(textArea);

    public MultiLineTableCellEditor(){
        textArea.setLineWrap(true);
        textArea.registerKeyboardAction(this
                , KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK)
                , JComponent.WHEN_FOCUSED);
    }

    public Object getCellEditorValue(){
        return textArea.getText();
    }

    /*--------------------------------[ clickCountToStart ]----------------------------------*/

    protected int clickCountToStart = 2;

    public int getClickCountToStart(){
        return clickCountToStart;
    }

    public void setClickCountToStart(int clickCountToStart){
        this.clickCountToStart = clickCountToStart;
    }

    public boolean isCellEditable(EventObject e){
        return !(e instanceof MouseEvent)
                || ((MouseEvent)e).getClickCount()>=clickCountToStart;
    }

    /*--------------------------------[ ActionListener ]------------------------*/

    public void actionPerformed(ActionEvent ae){
        stopCellEditing();
    }

    /*---------------------------[ TableCellEditor ]------------------------*/

    public Component getTableCellEditorComponent(JTable table, Object value
            , boolean isSelected, int row, int column){
        String text = value!=null ? value.toString() : "";
        textArea.setText(text);
        textArea.setCaretPosition(0);
        return scrollPane;
    }

    /*-------------------------------------------------[ ResizableTextArea ]---------------------------------------------------*/

    public static final String UPDATE_BOUNDS = "UpdateBounds";

    static class ResizableScrollPane extends JScrollPane implements MyTable.AutoResizable{
        ResizableScrollPane(JTextArea view){
            super(view);
        }

        private JTextArea getTextArea(){
            return (JTextArea)getViewport().getView();
        }

        public void setBounds(int x, int y, int width, int height){
            if(Boolean.TRUE.equals(getClientProperty(UPDATE_BOUNDS)))
                super.setBounds(x, y, width, height);
        }

        @Override
        public void requestFocus(){
            getTextArea().requestFocus();
        }

        public void addNotify(){
            super.addNotify();
            getTextArea().getDocument().addDocumentListener(listener);
        }

        public void removeNotify(){
            getTextArea().getDocument().removeDocumentListener(listener);
            super.removeNotify();
        }

        DocumentListener listener = new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                doResize();
            }

            public void removeUpdate(DocumentEvent e){
                doResize();
            }

            public void changedUpdate(DocumentEvent e){
                doResize();
            }
        };

        private void doResize(){
            final JTable table = (JTable)SwingUtilities.getAncestorOfClass(JTable.class, this);
            if(table.isEditing()){
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        doResize(table, table.getEditingRow(), table.getEditingColumn());
                        revalidate();
                    }
                });
            }
        }

        public void doResize(JTable table, int row, int col){
            Rectangle cellRect = table.getCellRect(row, col, false);
            Dimension prefSize = getTextArea().getPreferredScrollableViewportSize();
            putClientProperty(UPDATE_BOUNDS, Boolean.TRUE);
            Insets insets = getInsets();
            int width = Math.max(cellRect.width, prefSize.width)+insets.left+insets.right;
            int height = Math.max(cellRect.height, prefSize.height)+insets.top+insets.bottom;
            height = Math.min(table.getHeight()- cellRect.y, height);
            setBounds(cellRect.x, cellRect.y, width, height);
            putClientProperty(UPDATE_BOUNDS, Boolean.FALSE);
        }
    }
}