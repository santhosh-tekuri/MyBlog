package santhosh;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;

public class MoreButtonDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.setFloatable(false);

        String icons[] = {
                "build.gif",
                "buildAll.gif",
                "clean.gif",
                "cleanAll.gif",
                "clone.gif",
                "compile.gif",
                "compileAll.gif",
                "copy.gif",
                "cut.gif",
                "delete.gif",
                "execute.gif",
                "find.gif",
                "nextTab.gif",
                "open.gif",
                "openLocalExplorer.gif",
                "paste.gif",
                "previousTab.gif",
                "properties.gif",
                "redo.gif",
                "undo.gif",
                "undock.gif",
        };

        for(int i = 1; i<icons.length; i++){
            AbstractAction action = new AbstractAction(icons[i].substring(0, icons[i].length()-".gif".length())){
                public void actionPerformed(ActionEvent e){
                    JOptionPane.showMessageDialog((Component)e.getSource(), getValue(Action.NAME)+" is performed.");
                }
            };
            action.putValue(Action.SMALL_ICON, new ImageIcon(MoreButtonDemo.class.getResource(icons[i])));
            toolbar.add(action);
            if(i%4==0 && i!=icons.length-1)
                toolbar.addSeparator();
        }

        JFrame frame = new JFrame("JToolbar with \"more\" button - santhosh@in.fiorano.com");
        frame.getContentPane().add(MoreButton.wrapToolBar(toolbar), BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(new JTextArea());
        frame.getContentPane().add(scroll);

        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
