package santhosh;

import javax.swing.border.Border;
import java.awt.event.MouseEvent;

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
 */
public interface ResizableBorder extends Border {
    /**
     * @return one of the following constants from Cursor class
     * Cursor.N_RESIZE_CURSOR
     * Cursor.S_RESIZE_CURSOR
     * Cursor.W_RESIZE_CURSOR
     * Cursor.E_RESIZE_CURSOR
     * Cursor.NW_RESIZE_CURSOR
     * Cursor.NE_RESIZE_CURSOR
     * Cursor.SW_RESIZE_CURSOR
     * Cursor.SE_RESIZE_CURSOR
     * Cursor.MOVE_CURSOR // move the compnent without resizing it
     * Cursor.DEFAULT_CURSOR // do nothing i.e, no grip point
     */
    public int getResizeCursor(MouseEvent me);
}