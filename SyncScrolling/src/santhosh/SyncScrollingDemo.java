package santhosh;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.io.FileReader;
import java.io.File;

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
public class SyncScrollingDemo{
    public static void main(String[] args) throws Exception{
        JTextArea t1 = new JTextArea();
        JTextArea t2 = new JTextArea();

        new DefaultEditorKit().read(
                SyncScrollingDemo.class.getResourceAsStream("SyncScrollingDemo.txt")
                , t1.getDocument(), 0);
        t2.setText(t1.getText());


        JScrollPane scroll1 = new JScrollPane(t1);
        JScrollPane scroll2 = new JScrollPane(t2);
        scroll1.getVerticalScrollBar().setModel(scroll2.getVerticalScrollBar().getModel());

        JFrame frame = new JFrame("Sync Vertical ScrollBars - santhosh@in.fiorano.com");
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll1, scroll2);
        split.setResizeWeight(0.5d);
        frame.getContentPane().add(split);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
