package santhosh;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class JComboBoxBug{
    public static void main(String[] args){
        final JComboBox combo = new JComboBox();
        combo.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                System.out.println("item States changed:"+e.getItem() +" "+ (e.getStateChange()==ItemEvent.SELECTED ? "Selected": "Deselected"));
            }
        });
        System.out.println("currenlty selelected item: "+combo.getSelectedItem());
        combo.setModel(new DefaultComboBoxModel(new String[]{"item1", "Item2", "Item3"}));
        System.out.println("model changed.");
        System.out.println("currenlty selelected item: "+combo.getSelectedItem());
    }
}
