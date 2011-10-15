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

package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComponentTitledBorderDemo{
    static ComponentTitledBorder componentBorder;
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        final JCheckBox checkBox = new JCheckBox("Use Proxy", true);
        checkBox.setToolTipText("this is tooltip");
        final JPanel proxyPanel = new TitledJPanel(checkBox, BorderFactory.createEtchedBorder());
        proxyPanel.add(new JLabel("Proxy Host: "));
        proxyPanel.add(new JTextField("proxy.xyz.com"));
        proxyPanel.add(new JLabel("  Proxy Port"));
        proxyPanel.add(new JTextField("8080"));

        checkBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean enable = checkBox.isSelected();
                Component comp[] = proxyPanel.getComponents();
                for(int i = 0; i<comp.length; i++){
                    comp[i].setEnabled(enable);
                }
            }
        });
        JFrame frame = new JFrame("ComponentTitledBorder - santhosh@in.fiorano.com");
        Container contents = frame.getContentPane();
        contents.setLayout(new FlowLayout());
        contents.add(proxyPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
