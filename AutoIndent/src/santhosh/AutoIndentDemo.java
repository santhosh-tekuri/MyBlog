package santhosh;

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

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Santhosh Kumar T
 * @email santhosh@fiorano.com
 */
public class AutoIndentDemo {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
            // ignore
        }
        JTextArea textArea = new JTextArea("public class santhosh.AutoIndentAction extends AbstractAction {\n" +
                "    public void actionPerformed(ActionEvent ae) {\n" +
                "        JTextArea comp = (JTextArea)ae.getSource();\n" +
                "        Document doc = comp.getDocument();", 20, 100);

        textArea.registerKeyboardAction(new AutoIndentAction(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);

        JFrame frame = new JFrame("Autoindent for JTextArea - santhosh@fiorano.com");
        ((JPanel) frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.getContentPane().add(new JScrollPane(textArea));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
