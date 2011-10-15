package santhosh;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeCellRenderer;
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

/**
 * @author Santhosh Kumar T
 * @email  santhosh@in.fiorano.com
 */
public class HighlighterTree extends JTree{
    private TreePath highlightPath = null;
    private Color highlightColor = new Color(255, 255, 204);

    public TreePath getHighlightPath(){
        return highlightPath;
    }

    public void setHighlightPath(TreePath highlightPath){
        this.highlightPath = highlightPath;
        treeDidChange();
    }

    protected void paintComponent(Graphics g){
        // paint background ur self
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(),getHeight());

        // paint the highlight if any
        g.setColor(highlightColor);
        int fromRow = getRowForPath(highlightPath);
        if(fromRow!=-1){
            int toRow = fromRow;
            int rowCount = getRowCount();
            while(toRow<rowCount){
                TreePath path = getPathForRow(toRow);
                if(highlightPath.isDescendant(path))
                    toRow++;
                else
                    break;
            }
            Rectangle fromBounds = getRowBounds(fromRow);
            Rectangle toBounds = getRowBounds(toRow-1);
            g.fillRect(0, fromBounds.y, getWidth(), toBounds.y-fromBounds.y+toBounds.height);
        }

        setOpaque(false); // trick not to paint background
        super.paintComponent(g);
        setOpaque(true);
    }
}

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

/**
 * Trick to make renderer transparent when unselected
 *
 * @author Santhosh Kumar T
 * @email  santhosh@in.fiorano.com
 */
class MyTreeCellRenderer extends DefaultTreeCellRenderer{
    public MyTreeCellRenderer(){
        setBackgroundNonSelectionColor(null);
    }

    public Color getBackground(){
        return null;
    }
}