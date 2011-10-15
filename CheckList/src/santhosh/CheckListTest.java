package santhosh;

import javax.swing.*;

public class CheckListTest{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        String data[] = new String[10];
        for(int i = 0; i<data.length; i++)
            data[i] = "Item "+(i+1);

        JList list = new JList(data);
        // make your JList as check list
        CheckListManager checkListManager = new CheckListManager(list);


        JFrame frame = new JFrame("JList with CheckBoxes - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(list));
        frame.setSize(200, 300);
        frame.setVisible(true);
    }
}
