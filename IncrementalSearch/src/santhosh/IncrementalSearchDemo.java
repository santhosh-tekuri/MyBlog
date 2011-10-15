package santhosh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.util.Vector;

public class IncrementalSearchDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JTabbedPane tabPane = new JTabbedPane();

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        new TextComponentFindAction().install(textArea);
        try{
            new DefaultEditorKit().read(IncrementalSearchDemo.class.getResourceAsStream("FindAction.txt"), textArea.getDocument(), 0);
        } catch(Exception e){
            e.printStackTrace();
        }
        tabPane.addTab("JTextComponent", new JScrollPane(textArea));

        JTree tree = new JTree();
        new TreeFindAction().install(tree);
        Vector data = new Vector();
        for(int i=0; i<tree.getRowCount(); i++){
            tree.expandRow(i);
            data.add(tree.getPathForRow(i).getLastPathComponent().toString());
        }
        tabPane.addTab("JTree", new JScrollPane(tree));

        JList list = new JList(data);
        new ListFindAction().install(list);
        tabPane.addTab("JList", new JScrollPane(list));

        JComboBox combo = new JComboBox(data);
        new ComboFindAction().install(combo);
        JPanel panel = new JPanel();
        panel.add(new JLabel("ComboBox: "));
        panel.add(combo);
        tabPane.addTab("JComboBox", panel);

        JTable table = new JTable();
        new TableFindAction().install(table);
        String columns[] = new String[]{
            "From",
            "Subject",
            "Size in KB",
        };

        String tableData[][] = new String[][]{
            { "santhosh@in.fiorano.com", "what is the problem", "5"},
            { "stroustrup@belllabs.com", "when can i find sources", "1"},
            { "java@sun.com", "this is test message", "10"},
            { "developer@sun.com", "try jdk1.6", "50"},
        };

        table.setModel(new DefaultTableModel(tableData, columns));
        tabPane.addTab("JTable", new JScrollPane(table));

        JFrame frame = new JFrame("Incremental Search for Swing Components - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tabPane);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size.width-100, size.height-100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
