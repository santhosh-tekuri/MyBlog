package santhosh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GloballyContextSensitiveActionsDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        Action action = new GloballyContextSensitiveAction("selectAll", "selectAll", "selectAll");
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        menu.add(action);
        menubar.add(menu);

        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);
        toolbar.setFloatable(true);
        toolbar.add(action);

        JPanel contents = new JPanel();

        String[] listData = new String[]{"item1", "item2", "item3", "item4", "item5            "};
        JList list = new JList(listData);
        contents.add(new JScrollPane(list));

        JTree tree = new JTree();
        tree.setVisibleRowCount(10);
        contents.add(new JScrollPane(tree));

        JTable table = new JTable(new DefaultTableModel(
                new String[]{"Name", "Type", "Modified"}
                , 10)
        );
        table.setPreferredScrollableViewportSize(
                new Dimension(100, 5*table.getRowHeight()));
        contents.add(new JScrollPane(table));

        JFrame frame = new JFrame("Globally Context Sensitive Actions - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menubar);
        frame.getContentPane().add(contents);
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);

        frame.pack();
        frame.show();
    }
}
