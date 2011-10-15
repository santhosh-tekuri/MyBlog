/**
 * Copyright (c) 2005-2006, Fiorano Software Technologies Pvt. Ltd.,
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Fiorano Software ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * enclosed with this product or entered into with Fiorano.
 */
package santhosh;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

// got this workaround from the following bug:
//      http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607
public class WideComboBox extends JComboBox{

    public WideComboBox() {
    }

    public WideComboBox(final Object items[]){
        super(items);
    }

    public WideComboBox(Vector items) {
        super(items);
    }

    public WideComboBox(ComboBoxModel aModel) {
        super(aModel);
    }

    private boolean layingOut = false;

    public void doLayout(){
        try{
            layingOut = true;
            super.doLayout();
        }finally{
            layingOut = false;
        }
    }

    public Dimension getSize(){
        Dimension dim = super.getSize();
        if(!layingOut)
            dim.width = Math.max(dim.width, getPreferredSize().width);
        return dim;
    }
}