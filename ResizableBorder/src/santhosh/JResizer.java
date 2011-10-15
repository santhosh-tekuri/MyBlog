package santhosh;

import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
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
public class JResizer extends JComponent {

    public JResizer(Component comp){
        this(comp, new DefaultResizableBorder(6));
    }

    public JResizer(Component comp, ResizableBorder border){
        setLayout(new BorderLayout());
        add(comp);
        setBorder(border);
    }

    public void setBorder(Border border){
        removeMouseListener(resizeListener);
        removeMouseMotionListener(resizeListener);
        if(border instanceof ResizableBorder){
            addMouseListener(resizeListener);
            addMouseMotionListener(resizeListener);
        }
        super.setBorder(border);
    }

    private void didResized(){
        if(getParent()!=null){
            getParent().repaint();
            invalidate();
            ((JComponent)getParent()).revalidate();
        }
    }

    MouseInputListener resizeListener = new MouseInputAdapter(){
        public void mouseMoved(MouseEvent me){
            ResizableBorder border = (ResizableBorder)getBorder();
            setCursor(Cursor.getPredefinedCursor(border.getResizeCursor(me)));
        }

        public void mouseExited(MouseEvent mouseEvent){
            setCursor(Cursor.getDefaultCursor());
        }

        private int cursor;
        private Point startPos = null;

        public void mousePressed(MouseEvent me){
            ResizableBorder border = (ResizableBorder)getBorder();
            cursor = border.getResizeCursor(me);
            startPos = me.getPoint();
        }

        public void mouseDragged(MouseEvent me){
            if(startPos!=null){
                int dx = me.getX()-startPos.x;
                int dy = me.getY()-startPos.y;
                switch(cursor){
                    case Cursor.N_RESIZE_CURSOR:
                        setBounds(getX(), getY()+dy, getWidth(), getHeight()-dy);
                        didResized();
                        break;
                    case Cursor.S_RESIZE_CURSOR:
                        setBounds(getX(), getY(), getWidth(), getHeight()+dy);
                        startPos = me.getPoint();
                        didResized();
                        break;
                    case Cursor.W_RESIZE_CURSOR:
                        setBounds(getX()+dx, getY(), getWidth()-dx, getHeight());
                        didResized();
                        break;
                    case Cursor.E_RESIZE_CURSOR:
                        setBounds(getX(), getY(), getWidth()+dx, getHeight());
                        startPos = me.getPoint();
                        didResized();
                        break;
                    case Cursor.NW_RESIZE_CURSOR:
                        setBounds(getX()+dx, getY()+dy, getWidth()-dx, getHeight()-dy);
                        didResized();
                        break;
                    case Cursor.NE_RESIZE_CURSOR:
                        setBounds(getX(), getY()+dy, getWidth()+dx, getHeight()-dy);
                        startPos = new Point(me.getX(), startPos.y);
                        didResized();
                        break;
                    case Cursor.SW_RESIZE_CURSOR:
                        setBounds(getX()+dx, getY(), getWidth()-dx, getHeight()+dy);
                        startPos = new Point(startPos.x, me.getY());
                        didResized();
                        break;
                    case Cursor.SE_RESIZE_CURSOR:
                        setBounds(getX(), getY(), getWidth()+dx, getHeight()+dy);
                        startPos = me.getPoint();
                        didResized();
                        break;
                    case Cursor.MOVE_CURSOR:
                        Rectangle bounds = getBounds();
                        bounds.translate(dx, dy);
                        setBounds(bounds);
                        didResized();
                }

                // cursor shouldn't change while dragging
                setCursor(Cursor.getPredefinedCursor(cursor));
            }
        }

        public void mouseReleased(MouseEvent mouseEvent){
            startPos = null;
        }
    };
}
