package santhosh;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

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
 */
public class StringActionTableCellEditor extends ActionTableCellEditor{
    public StringActionTableCellEditor(TableCellEditor editor){
        super(editor);
    }

    protected void editCell(JTable table, int row, int column){
        JTextArea textArea = new JTextArea(10, 50);
        Object value = table.getValueAt(row, column);
        if(value!=null){
            textArea.setText((String)value);
            textArea.setCaretPosition(0);
        }
        int result = JOptionPane.showOptionDialog(table
                , new JScrollPane(textArea), (String)table.getColumnName(column)
                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        if(result==JOptionPane.OK_OPTION)
            table.setValueAt(textArea.getText(), row, column);
    }
}
