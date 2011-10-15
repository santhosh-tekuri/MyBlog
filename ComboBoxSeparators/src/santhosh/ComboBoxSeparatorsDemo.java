package santhosh;

import javax.swing.*;

public class ComboBoxSeparatorsDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JComboBox comboBox = new JComboBox(new String[]{
            "All", "Unread", "Important", "Work", "Later", "People I Know", "Customize..."
        });

        comboBox.setRenderer(new ComboSeparatorsRenderer(comboBox.getRenderer()){
            protected boolean addSeparatorAfter(JList list, Object value, int index){
                return "Unread".equals(value) || "People I Know".equals(value);
            }
        });

        JOptionPane.showMessageDialog(null, comboBox, "JComboBox Items with Separators - santhosh.tekuri@gmail.com", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
