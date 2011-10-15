package santhosh;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class ExpandMenuItem extends JMenuItem implements ActionListener{
    public ExpandMenuItem(){
        super("Expand");
        addActionListener(this);
    }

    private void showPersonalizedItems(boolean show){
        JMenu menu = (JMenu)((JPopupMenu)getParent()).getInvoker();

        // show/hide all personalized menu items
        Component[] comp = menu.getMenuComponents();
        for(int i = 0; i<comp.length; i++){
            if(comp[i] instanceof JMenuItem){
                JComponent jc = (JComponent)comp[i];
                if(Boolean.TRUE.equals(jc.getClientProperty(PERSONALIZED)))
                    jc.setVisible(show);
            }
        }

        // hide the separator before this menu item
        comp[comp.length-2].setVisible(!show);

        // hide this menu item
        setVisible(!show);
    }

    /*-------------------------------------------------[ ActionListener ]---------------------------------------------------*/
    
    public void actionPerformed(ActionEvent e){
        showPersonalizedItems(true);

        // reopen the menu
        JMenu menu = (JMenu)((JPopupMenu)getParent()).getInvoker();
        MenuElement newPath[] = new MenuElement[4];
        newPath[0] = (MenuElement)menu.getParent();
        newPath[1] = menu;
        newPath[2] = menu.getPopupMenu();
        newPath[3] = (MenuElement)menu.getMenuComponent(0); // any menu item
        MenuSelectionManager.defaultManager().setSelectedPath(newPath);
    }

    /*-------------------------------------------------[ PopupMenuListener ]---------------------------------------------------*/

    PopupMenuListener listener = null;
    public void addNotify(){
        super.addNotify();
        if(listener==null){
            JPopupMenu popup = (JPopupMenu)getParent();
            popup.addPopupMenuListener(new PopupMenuListener(){
                // when popup menu closed, again restore previous state
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
                    showPersonalizedItems(false);
                }
                public void popupMenuCanceled(PopupMenuEvent e){}
                public void popupMenuWillBecomeVisible(PopupMenuEvent e){}
            });
        }
    }

    /*-------------------------------------------------[ Timer ]---------------------------------------------------*/

    public void menuSelectionChanged(boolean isIncluded){
        super.menuSelectionChanged(isIncluded);
        if(isIncluded){
            timer.setRepeats(false);
            timer.start();
        }else
            timer.stop();
    }

    Timer timer = new Timer(1500, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            MenuSelectionManager.defaultManager().clearSelectedPath();
            doClick();
        }
    });

    /*-------------------------------------------------[ Make Personalized ]---------------------------------------------------*/

    public static final String PERSONALIZED = "Personalized";

    public static void setPersonalized(JMenuItem menuItem){
        menuItem.putClientProperty(PERSONALIZED, Boolean.TRUE);
        menuItem.setVisible(false);
    }
}