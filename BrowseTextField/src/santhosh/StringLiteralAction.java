package santhosh;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class StringLiteralAction extends AbstractAction{

    public StringLiteralAction(){
        super("String Literal");
    }

    public void actionPerformed(ActionEvent ae){
        JTextComponent tcomp = (JTextComponent)ae.getSource();
        String text = fromJavaStringLiteral(tcomp.getText());

        JTextArea textArea = new JTextArea(text, 10, 30);
        int response = JOptionPane.showOptionDialog(tcomp, new JScrollPane(textArea),
                (String)getValue(Action.NAME), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, null, null);

        if(response==JOptionPane.OK_OPTION){
            tcomp.setText(toJavaStringLiteral(textArea.getText(), false));
            tcomp.setCaretPosition(0);
        }
    }

    /*-------------------------------------------------[ Utilitties ]---------------------------------------------------*/

    // NOTE: the return value is not surrounded by double-quotes
    public static String toJavaStringLiteral(String str, boolean useRaw) {
        StringBuffer buf = new StringBuffer(str.length() * 6); // x -> \u1234
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            switch (c) {
            case '\b': buf.append("\\b"); break; // NOI18N
            case '\t': buf.append("\\t"); break; // NOI18N
            case '\n': buf.append("\\n"); break; // NOI18N
            case '\f': buf.append("\\f"); break; // NOI18N
            case '\r': buf.append("\\r"); break; // NOI18N
            case '\"': buf.append("\\\""); break; // NOI18N
            case '\\': buf.append("\\\\"); break; // NOI18N
            default:
                if (c >= 0x0020 && (useRaw || c <= 0x007f))
                    buf.append(c);
                else {
                    buf.append("\\u"); // NOI18N
                    String hex = Integer.toHexString(c);
                    for (int j = 0; j < 4 - hex.length(); j++)
                        buf.append('0');
                    buf.append(hex);
                }
            }
        }
        return buf.toString();
    }

    // NOTE: the argument should not be surrounded by double-quotes
    public static String fromJavaStringLiteral(String str){
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);

            switch(c){
                case '\\':
                    if(i == str.length() - 1){
                        buf.append('\\');
                        break;
                    }
                    c = str.charAt(++i);
                    switch(c){
                        case 'n':
                            buf.append('\n');
                            break;
                        case 't':
                            buf.append('\t');
                            break;
                        case 'r':
                            buf.append('\r');
                            break;
                        case 'u':
                            int value=0;
                            for (int j=0; j<4; j++) {
                                c = str.charAt(++i);
                                switch (c) {
                                    case '0': case '1': case '2': case '3': case '4':
                                    case '5': case '6': case '7': case '8': case '9':
                                        value = (value << 4) + c - '0';
                                        break;
                                    case 'a': case 'b': case 'c':
                                    case 'd': case 'e': case 'f':
                                        value = (value << 4) + 10 + c - 'a';
                                        break;
                                    case 'A': case 'B': case 'C':
                                    case 'D': case 'E': case 'F':
                                        value = (value << 4) + 10 + c - 'A';
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                                }
                            }
                            buf.append((char)value);
                            break;
                        default:
                            buf.append(c);
                            break;
                    }
                    break;
                default:
                    buf.append(c);
            }
        }
        return buf.toString();
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JTextField textField = new JTextField(toJavaStringLiteral("This is line1.\n\tthis line2.\n\t\tthis is line3.", true));
        try{
            textField.setFont(new Font("DialogInput", Font.PLAIN, 11));
        } catch(Exception e){
            e.printStackTrace();
        }
        textField.setMargin(new Insets(1, 3, 1, 3));
        JFrame frame = new JFrame("Multi Line Text using JTextField - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JLabel("Description: "), BorderLayout.WEST);
        frame.getContentPane().add(BrowseButton.createBrowsePanel(textField, new StringLiteralAction()));
        frame.pack();
        frame.setResizable(false);
        frame.show();
    }
}
