package santhosh;

import javax.swing.*;
import java.awt.*;

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
public class SnapSplitPaneDividerDemo extends JFrame{
    public SnapSplitPaneDividerDemo(){
        super("Snap Divider - santhosh.tekuri@gmail.com");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTree tree = new JTree();
        tree.expandRow(1);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(new JTextArea(15, 50))){
            // overridden to mape divider snap while dragging
            public void setDividerLocation(int location){
                Dimension prefSize = getLeftComponent().getPreferredSize();
                int pref = (getOrientation()==HORIZONTAL_SPLIT ? prefSize.width : prefSize.height) + getDividerSize()/2;
                super.setDividerLocation(Math.abs(pref-location)<=10 ? pref : location);
            }
        };

        split.setContinuousLayout(true);

        getContentPane().add(split);

        pack();
    }

    public static void main(String[] args){
        new SnapSplitPaneDividerDemo().setVisible(true);
    }
}
