/**
 * JLibs: Common Utilities for Java
 * Copyright (C) 2009  Santhosh Kumar T
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

package santhosh;

import java.awt.*;

/**
 * @author Santhosh Kumar T
 * @unused
 */
public class Bounds{
    public static Rectangle enclose(Dimension parent, Rectangle child){
        int x = child.x;
        int y = child.y;
        int width = child.width;
        int height = child.height;

        int xout = child.x + child.width-parent.width;
        if(xout>0){
            int xalloc = Math.min(child.x, xout);
            x = x-xalloc;
            width = parent.width - x;
        }

        int yout = child.y + child.height-parent.height;
        if(yout>0){
            int yalloc = Math.min(child.y, yout);
            y = y-yalloc;
            height = parent.height - y;
        }

        return new Rectangle(x, y, width, height);
    }
}
