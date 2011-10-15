package santhosh;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.StringTokenizer;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class SelectPeopleAction extends AbstractAction{
    private final static String people[] = {"Santhosh", "Rick", "Matt", "Lorimer", "Scott"};

    public SelectPeopleAction(){
        super("selectPeople");
    }

    public void actionPerformed(ActionEvent e){
        HyperlinkEvent hle = (HyperlinkEvent)e.getSource();

        try{
            Element elem = hle.getSourceElement();
            Document doc = elem.getDocument();
            int start = elem.getStartOffset();
            int end = elem.getEndOffset();
            String link = doc.getText(start, end-start);
            link = link.equals("contains people") ? "" : link.substring("contains ".length());

            JList list = new JList(people);
            StringTokenizer stok = new StringTokenizer(link, ", ");
            while(stok.hasMoreTokens()){
                String token = stok.nextToken();
                for(int i = 0; i<list.getModel().getSize(); i++){
                    if(list.getModel().getElementAt(i).equals(token))
                        list.getSelectionModel().addSelectionInterval(i, i);
                }
            }

            int response = JOptionPane.showOptionDialog((Component)hle.getSource(), new JScrollPane(list), "People",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, null, null);
            if(response==JOptionPane.OK_OPTION){
                String newLink = "";
                Object selected[] = list.getSelectedValues();
                for(int i = 0; i<selected.length; i++){
                    if(i!=0)
                        newLink += ", ";
                    newLink += selected[i];
                }
                newLink = newLink.length()==0 ? "contains people" : "contains "+newLink;
                elem.getDocument().remove(start, end-start);
                elem.getDocument().insertString(start, newLink, elem.getAttributes());
            }
        } catch(BadLocationException ex){
            ex.printStackTrace();
        }
    }
}
