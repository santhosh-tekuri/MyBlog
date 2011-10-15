package santhosh;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class ContextSensitiveActionsDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        String[] listData = new String[]{"item1", "item2", "item3", "item4", "item5"};

        JList list1 = new JList(listData);
        JPanel listPanel1 = new JPanel(new BorderLayout());
        listPanel1.add(new JScrollPane(list1), BorderLayout.CENTER);
        Action action1 = list1.getActionMap().get("selectAll");
        listPanel1.add(new JButton(action1), BorderLayout.SOUTH);
        listPanel1.setBorder(BorderFactory.createTitledBorder("Plain Action"));

        JList list2 = new JList(listData);
        JPanel listPanel2 = new JPanel(new BorderLayout());
        listPanel2.add(new JScrollPane(list2), BorderLayout.CENTER);
        Action action2 = new ContextSensitiveAction(list2.getActionMap().get("selectAll"), list2);
        listPanel2.add(new JButton(action2), BorderLayout.SOUTH);
        listPanel2.setBorder(BorderFactory.createTitledBorder("Context Sensitive Action"));

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(listPanel1);
        panel.add(listPanel2);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt){
                System.err.println("name:"+evt.getPropertyName());
                System.err.println("old:"+evt.getOldValue());
                System.err.println("new:"+evt.getNewValue());
            }
        });

        JFrame frame = new JFrame("ContextSensiveActions - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setSize(new Dimension(400, 400));
        frame.setVisible(true);
    }
}
