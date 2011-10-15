package santhosh;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

public class ListAutoCompleter extends AutoCompleter{

    private List completionList;
    private boolean ignoreCase;

    public ListAutoCompleter(JTextComponent comp, List completionList, boolean ignoreCase){
        super(comp);
        this.completionList = completionList;
        this.ignoreCase = ignoreCase;
    }

    protected int getTokenStart(){
        try{
            int start = Utilities.getWordStart(textComp, textComp.getCaretPosition());
            String value = textComp.getText(start, textComp.getCaretPosition()-start);
            if(value.trim().length()==0)
                return textComp.getCaretPosition();
            else
                return start;
        }catch(BadLocationException blex){
            blex.printStackTrace();
            return 0;
        }
    }

    protected int getTokenEnd(){
        try{
            return Utilities.getWordEnd(textComp, textComp.getCaretPosition());
        }catch(BadLocationException blex){
            blex.printStackTrace();
            return textComp.getDocument().getLength();
        }
    }

    // update classes model depending on the data in textfield
    protected boolean updateListData(){
        try{
            int start = getTokenStart();

            String value = textComp.getText(start, textComp.getCaretPosition()-start);
            int substringLen = value.length();

            List possibleStrings = new ArrayList();
            Iterator iter = completionList.iterator();
            while(iter.hasNext()){
                String listEntry = (String)iter.next();
                if(substringLen>=listEntry.length())
                    continue;

                if(ignoreCase){
                    if(value.equalsIgnoreCase(listEntry.substring(0, substringLen)))
                        possibleStrings.add(listEntry);
                }else if(listEntry.startsWith(value))
                    possibleStrings.add(listEntry);
            }

            list.setListData(possibleStrings.toArray());
            return true;
        }catch(BadLocationException blex){
            return false;
        }
    }

    // user has selected some item in the classes. update textfield accordingly...
    protected void acceptedListItem(String selected){
        if(selected==null)
            return;

//        int start = getTokenStart();
//        int end = getTokenEnd();
//
//        try{
//            textComp.getDocument().remove(start, end-start);
//            textComp.getDocument().insertString(start, selected, null);
//        } catch(BadLocationException e){
//            e.printStackTrace();
//        }

        int caret = textComp.getCaretPosition();
        int start = getTokenStart();

        try{
            textComp.getDocument().remove(start, caret-start);
            textComp.getDocument().insertString(start, selected, null);
        } catch(BadLocationException e){
            e.printStackTrace();
        }

        popup.setVisible(false);
    }

    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//        JFormattedTextField tf = new JFormattedTextField(){
//            public void setText(String t){
//                System.err.println("text: "+t);
//                super.setText(t);
//            }
//        };
//        tf.setValue("JS");
        JTextField tf = new JTextField();
        tf.setText("JS");
        new ListAutoCompleter(tf, Arrays.asList(new String[]{"JFC", "JSF", "JSP", "JAXP", "JDBC", "J2EE"}), true);
        JFrame frame = new JFrame("List Based AutoCompleter - santhosh@in.fiorano.com");
        frame.setLocation(-100, 100);
        JOptionPane.showMessageDialog(frame, tf, "Choose Java Technology", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
