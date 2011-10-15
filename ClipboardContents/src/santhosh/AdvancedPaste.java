package santhosh;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

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
public class AdvancedPaste implements KeyEventPostProcessor{
    public static KeyStroke keyStroke =
            KeyStroke.getKeyStroke(KeyEvent.VK_V
                    , KeyEvent.CTRL_MASK|KeyEvent.SHIFT_MASK);

    private AdvancedPaste(){}

    static{
        MyFlavorListener.contents.size();
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventPostProcessor(new AdvancedPaste());
    }

    public boolean postProcessKeyEvent(KeyEvent ke){
        if(!KeyStroke.getKeyStrokeForEvent(ke).equals(keyStroke))
            return true;
        if(!(ke.getSource() instanceof JComponent))
            return true;

        JComponent comp = (JComponent)ke.getSource();
        TransferHandler transferHandler = comp.getTransferHandler();
        if(transferHandler==null ||
                !transferHandler.canImport(comp, new DataFlavor[]{ DataFlavor.stringFlavor}))
            return true;

        if(MyFlavorListener.contents.size()==0)
            return true;

        String result = MyFlavorListener.contents.get(0);
        if(MyFlavorListener.contents.size()>1){
            JList list = new JList(MyFlavorListener.contents);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);
            list.setPrototypeCellValue("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
            int response = JOptionPane.showOptionDialog(comp, new JScrollPane(list), "Choose content to paste"
                    , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                    , null, null, null);
            if(response!=JOptionPane.OK_OPTION)
                return true;
            result = String.valueOf(list.getSelectedValue());
        }

        if(result!=null)
            return !transferHandler.importData(comp, new StringSelection(result));

        return false;
    }
}