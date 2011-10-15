package santhosh;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;

public class ListItemSelection extends TransferNotifierProxy{
    private JList list;
    private int index;

    public ListItemSelection(JList list){
        super(new StringSelection((String)list.getSelectedValue()));
        this.list = list;
        index = list.getSelectedIndex();
    }

    public void transferAccepted(){
        DefaultListModel model = (DefaultListModel)list.getModel();
        model.removeElementAt(index);
        super.transferAccepted();
    }
}
