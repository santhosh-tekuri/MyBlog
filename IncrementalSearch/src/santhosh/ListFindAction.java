package santhosh;

import javax.swing.*;
import javax.swing.text.Position;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ListFindAction extends ListModelFindAction{

    protected boolean changed(JComponent comp, String searchString, Position.Bias bias){
        JList list = (JList)comp;
        boolean startingFromSelection = true;
        int max = list.getModel().getSize();
        int increment = 0;
        if(bias!=null)
            increment = (bias==Position.Bias.Forward) ? 1 : -1;
        int startingRow = (list.getLeadSelectionIndex()+increment+max)%max;
        if(startingRow<0 || startingRow>= list.getModel().getSize()){
            startingFromSelection = false;
            startingRow = 0;
        }

        int index = getNextMatch(list.getModel(), searchString, startingRow, bias);
        if(index!=-1){
            changeSelection(list, index);
            return true;
        } else if(startingFromSelection){
            index = getNextMatch(list.getModel(), searchString, 0, bias);
            if(index!=-1){
                changeSelection(list, index);
                return true;
            }
        }
        return false;
    }

    protected void changeSelection(JList list, int index){
        if(controlDown)
            list.addSelectionInterval(index, index);
        else
            list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }
}
