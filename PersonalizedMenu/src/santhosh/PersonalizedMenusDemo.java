package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PersonalizedMenusDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JMenu menu = new JMenu("File");

        String icons[] = {
                "build.gif", "buildAll.gif", "clean.gif",
                "cleanAll.gif", "clone.gif", "compile.gif",
                "compileAll.gif", "copy.gif", "cut.gif",
                "delete.gif", "execute.gif", "find.gif",
                "nextTab.gif", "open.gif", "openLocalExplorer.gif",
                "paste.gif", "previousTab.gif", "properties.gif",
                "redo.gif", "undo.gif", "undock.gif",
        };

        JMenuItem menuItem[] = new JMenuItem[icons.length];

        for(int i = 1; i<icons.length; i++){
            AbstractAction action = new AbstractAction(icons[i].substring(0, icons[i].length()-".gif".length())){
                public void actionPerformed(ActionEvent e){
                    JOptionPane.showMessageDialog((Component)e.getSource(), getValue(Action.NAME)+" is performed.");
                }
            };
            action.putValue(Action.SMALL_ICON, new ImageIcon(PersonalizedMenusDemo.class.getResource(icons[i])));
            menuItem[i] = new JMenuItem(action);
            menu.add(menuItem[i]);
        }

        int personalized[] = {3, 4, 8, 9, 10, 13, 14, 15, 16, 18, 19, 20};
        for(int i=0; i<personalized.length; i++)
            ExpandMenuItem.setPersonalized(menuItem[personalized[i]]);

        menu.addSeparator();
        menu.add(new ExpandMenuItem());

        JFrame frame = new JFrame("Personalized Menus in Swing - santhosh@fiornao.com");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

