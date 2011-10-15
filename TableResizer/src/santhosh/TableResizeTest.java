package santhosh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class TableResizeTest{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        ResizableTable table = new ResizableTable(new DefaultTableModel(
                new String[]{"Name", "Type", "Modified", "Size", "Ratio", "Packed", "CRC", "Attributes", "Path"}
                , 10)
        );
        table.setResizable(true, true);

        JFrame frame = new JFrame("Resizable Table - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}
