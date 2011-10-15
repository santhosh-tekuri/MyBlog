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

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

/**
 * @author Santhosh Kumar T
 */
public class CheckTreeStateTest{
    public static void main(String[] args) throws IOException{
        final File file = new File("state.txt");
        JTree tree = new JTree();
        final CheckTreeManager checkTreeManager = new CheckTreeManager(tree, true, true);

        for(int i=0; i<tree.getRowCount(); i++)
            tree.expandRow(i);

        JFrame frame = new JFrame("CheckTreeStateTest");
        frame.getContentPane().add(new JScrollPane(tree));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(new JButton(new AbstractAction("OK"){
            public void actionPerformed(ActionEvent e){
                String state = TreePathUtil.serialize(checkTreeManager.getSelectionModel());
                try{
                    FileWriter writer = new FileWriter(file);
                    writer.write(state);
                    writer.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        }));
        buttons.add(new JButton(new AbstractAction("Cancel"){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        }));
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);

        if(file.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String state = reader.readLine();
            reader.close();
            TreePathUtil.deserialize(tree.getModel(), checkTreeManager.getSelectionModel(), state);
        }
        frame.pack();
        frame.setVisible(true);
    }
}
