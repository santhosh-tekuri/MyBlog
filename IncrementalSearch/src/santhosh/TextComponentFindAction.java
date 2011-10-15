package santhosh;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.io.FileReader;

// @author Santhosh Kumar T - santhosh@in.fiorano.com

public class TextComponentFindAction extends FindAction implements FocusListener{

    // 1. inits searchField with selected text
    // 2. adds focus listener so that textselection gets painted
    //    even if the textcomponent has no focus
    protected void initSearch(ActionEvent ae){
        super.initSearch(ae);
        JTextComponent textComp = (JTextComponent)ae.getSource();
        String selectedText = textComp.getSelectedText();
        if(selectedText!=null)
            searchField.setText(selectedText);
        searchField.removeFocusListener(this);
        searchField.addFocusListener(this);
    }

    protected boolean changed(JComponent comp, String str, Position.Bias bias){
        JTextComponent textComp = (JTextComponent)comp;
        int offset = bias==Position.Bias.Forward ? textComp.getCaretPosition() : textComp.getCaret().getMark()-1;

        int index = getNextMatch(textComp, str, offset, bias);
        if(index!=-1){
            textComp.select(index, index+str.length());
            return true;
        } else{
            offset = bias==null || bias==Position.Bias.Forward ? 0 : textComp.getDocument().getLength();
            index = getNextMatch(textComp, str, offset, bias);
            if(index!=-1){
                textComp.select(index, index+str.length());
                return true;
            } else
                return false;
        }
    }

    protected int getNextMatch(JTextComponent textComp, String str, int startingOffset, Position.Bias bias){
        String text = textComp.getText();

        if(ignoreCase){
            str = str.toUpperCase();
            text = text.toUpperCase();
        }

        return bias==null || bias==Position.Bias.Forward
                ? text.indexOf(str, startingOffset)
                : text.lastIndexOf(str, startingOffset);
    }

    /*-------------------------------------------------[ FocusListener ]---------------------------------------------------*/

    // ensures that the selection is visible
    // because textcomponent doesn't show selection
    // when they don't have focus
    public void focusGained(FocusEvent e){
        Caret caret = ((JTextComponent)comp).getCaret();
        caret.setVisible(true);
        caret.setSelectionVisible(true);
    }

    public void focusLost(FocusEvent e){
    }

    public static void main(String[] args) throws Exception{
        JTextArea textArea = new JTextArea(10, 50);
        textArea.setEditable(false);
        try{
            new DefaultEditorKit().read(new FileReader("D:\\MyBlog\\IncrementalSearch\\src\\santhosh\\TextComponentFindAction.java"),
                    textArea.getDocument(), 0);
        } catch(Exception e){
            e.printStackTrace();
        }

        FindAction findAction = new TextComponentFindAction();
        findAction.install(textArea);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(50, 10, 10, 10),
                scroll.getBorder()
        ));

        JOptionPane.showMessageDialog(null, scroll);
    }

}