package santhosh;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ExcelDemo extends JFrame{
    public ExcelDemo(){
        super("MultiLine in Table Cell Editing - as in Microsoft Excel  - santhosh@in.fiorano.com");

        String bigString = "This is book dedicated to swing.\nIt covers in-depth knowledge of swing";
        for(int i=0; i<10; i++)
            bigString = bigString+"\n"+bigString;
        JTable table = new MyTable(
                new Object[][]{
                        {"JavaTutorial", "This is sun's java tutorial.\nthis is best for beginners"},
                        {"JavaSwing", bigString},
                        {"", ""}
                },
                new String[]{"Title of Book", "Description of Book"}
        );
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

        TableColumn column1 = table.getColumn(table.getColumnName(1));
        column1.setCellEditor(new MultiLineTableCellEditor());

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(scroll);
        getContentPane().add(new JButton("East Button"), BorderLayout.EAST);
        getContentPane().add(new JButton("South Button"), BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        new ExcelDemo().setVisible(true);
    }
}