package santhosh;

import javax.swing.text.JTextComponent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class FileAutoCompleter extends AutoCompleter{
    public FileAutoCompleter(JTextComponent comp){
        super(comp);
    }

    protected boolean updateListData(){
        String value = textComp.getText();
        int index1 = value.lastIndexOf('\\');
        int index2 = value.lastIndexOf('/');
        int index = Math.max(index1, index2);
        if(index==-1)
            return false;
        String dir = value.substring(0, index+1);
        final String prefix = index==value.length()-1 ? null : value.substring(index + 1).toLowerCase();
        String[] files = new File(dir).list(new FilenameFilter(){
            public boolean accept(File dir, String name){
                return prefix!=null ? name.toLowerCase().startsWith(prefix) : true;
            }
        });
        if(files == null){
            list.setListData(new String[0]);
            return true;
        } else{
            if(files.length==1 && files[0].equalsIgnoreCase(prefix))
                list.setListData(new String[0]);
            else
                list.setListData(files);
            return true;
        }
    }

    protected void acceptedListItem(String selected){
        if(selected==null)
            return;

        String value = textComp.getText();
        int index1 = value.lastIndexOf('\\');
        int index2 = value.lastIndexOf('/');
        int index = Math.max(index1, index2);
        if(index==-1)
            return;
        int prefixlen = textComp.getDocument().getLength()-index-1;
        try{
            textComp.getDocument().insertString(textComp.getCaretPosition(), selected.substring(prefixlen), null);
        } catch(BadLocationException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Run - santhosh@in.fiorano.com");
        JPanel contents = (JPanel)frame.getContentPane();
        contents.setLayout(new BorderLayout(10, 10));
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel summary = new JPanel(new BorderLayout(10, 10));
        summary.add(new JLabel(new ImageIcon(FileAutoCompleter.class.getResource("run.gif"))), BorderLayout.WEST);
        JTextArea ta = new JTextArea("Type the name of a program, folder, document, or\nInternet resource, and Windows will open it for you.");
        ta.setFont(UIManager.getFont("Label.Font"));
        ta.setEditable(false);
        ta.setOpaque(false);
        summary.add(ta, BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Open: "), BorderLayout.WEST);
        final JTextField tf = new JTextField();
        new FileAutoCompleter(tf);
        panel.add(tf);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 0, 5, 5));
        buttonPanel.add(new JButton("OK"));
        buttonPanel.add(new JButton("Cancel"));
        buttonPanel.add(new JButton("Browse..."));
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.add(buttonPanel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(3);
        frame.getContentPane().add(summary, BorderLayout.NORTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}