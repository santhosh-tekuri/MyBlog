package santhosh;

import javax.swing.*;
import javax.swing.text.Position;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ComboFindAction extends ListModelFindAction{

    protected boolean changed(JComponent comp, String searchString, Position.Bias bias){
        JComboBox combo = (JComboBox)comp;
        boolean startingFromSelection = true;
        int max = combo.getModel().getSize();
        int increment = 0;
        if(bias!=null)
            increment = (bias == Position.Bias.Forward) ? 1 : -1;
        int startingRow = (combo.getSelectedIndex() + increment + max) % max;
        if (startingRow < 0 || startingRow >= combo.getModel().getSize()) {
            startingFromSelection = false;
            startingRow = 0;
        }

        int index = getNextMatch(combo.getModel(), searchString, startingRow, bias);
        if (index != -1) {
            combo.setSelectedIndex(index);
            return true;
        } else if (startingFromSelection) {
            index = getNextMatch(combo.getModel(), searchString, 0, bias);
            if (index != -1) {
                combo.setSelectedIndex(index);
                return true;
            }
        }
        return false;
    }
}

