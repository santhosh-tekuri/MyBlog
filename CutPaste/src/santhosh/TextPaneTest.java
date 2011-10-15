package santhosh;

import javax.swing.*;
import javax.swing.text.*;

public class TextPaneTest{
    public static void main(String[] args) throws BadLocationException{
        ImageIcon icon = new ImageIcon(TextPaneTest.class.getResource("user.gif"));
        ImageIcon icon1 = new ImageIcon(TextPaneTest.class.getResource("user.gif"));
        JTextPane textPane = new JTextPane();
        Document doc = textPane.getDocument();

        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setIcon(attrs, icon);

        MutableAttributeSet attrs1 = new SimpleAttributeSet();
        StyleConstants.setIcon(attrs1, icon1);

        doc.insertString(doc.getLength(), " ", attrs);
        doc.insertString(doc.getLength(), " ", null);
        doc.insertString(doc.getLength(), " ", attrs);
        doc.remove(doc.getLength()-2, 1);

        JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(textPane));
        frame.pack();
        frame.setVisible(true);
    }
}
