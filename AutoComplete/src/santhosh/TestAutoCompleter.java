package santhosh;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class TestAutoCompleter{

    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        JTextArea editor = new JTextArea();
        JTextField tf = new JTextField();
        List list = Arrays.asList(new String[]{"JFC", "JSF", "JSP", "JAXP", "JDBC", "J2EE"});

        ListCellRenderer renderer = new DefaultListCellRenderer(){
            ImageIcon icon = new ImageIcon(getClass().getResource("variable.gif"));
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
                JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setIcon(icon);
                return label;
            }
        };

        new ListAutoCompleter(editor, list, true).getJList().setCellRenderer(renderer);
        new ListAutoCompleter(tf, list, true).getJList().setCellRenderer(renderer);

        JFrame f = new JFrame("XmlEditor");
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(editor, BorderLayout.CENTER);
        f.getContentPane().add(tf, BorderLayout.SOUTH);

        f.setSize(600, 300);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
