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

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Jun 29, 2006
 * Time: 2:53:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class WideComboBoxDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignore){
        }
        JFrame frame = new JFrame("WideComboBox - santhosh@fiorano.com");

        JComboBox normalCombo = new JComboBox(new String[]{
                "this is very lengthy item1",
                "this is very lengthy item2",
                "this is very lengthy item3",
                "this is very lengthy item4",
                "this is very lengthy item5",
                "this is very lengthy item6",
        });
        JComboBox wideCombo = new WideComboBox(new String[]{
                "this is very lengthy item1",
                "this is very lengthy item2",
                "this is very lengthy item3",
                "this is very lengthy item4",
                "this is very lengthy item5",
                "this is very lengthy item6",
        });
        ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(normalCombo, BorderLayout.NORTH);
        frame.getContentPane().add(wideCombo, BorderLayout.SOUTH);
        frame.pack();
        frame.setSize(frame.getWidth()/2, frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
