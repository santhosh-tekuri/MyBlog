package santhosh.solution;

import santhosh.solution.MultiTransferable;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

public class MyListTransferableHandler extends TransferHandler{
    private DataFlavor flavor;

    public MyListTransferableHandler(Class transferableClass){
        flavor = new ObjectFlavor(transferableClass);
    }

    protected Transferable createTransferable(JComponent comp){
        JList list = (JList)comp;
        Object data = list.getSelectedValue();
        return new MultiTransferable(
            new Transferable[]{
                new ObjectTransferable(data),
                new StringSelection(data.toString())
            }
        );
    }

    public int getSourceActions(JComponent c){
        return COPY;
    }

    /*-------------------------------------------------[ Importing ]---------------------------------------------------*/

    public boolean canImport(JComponent c, DataFlavor[] flavors){
        for(int i = 0; i<flavors.length; i++){
            if(flavors[i].equals(flavor))
                return true;
        }
        return false;
    }

    public boolean importData(JComponent comp, Transferable t){
        JList list = (JList)comp;

        try{
            Object data = t.getTransferData(flavor);
            ((DefaultListModel)list.getModel()).addElement(data);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    protected void exportDone(JComponent source, Transferable t, int action){
        if(action==DnDConstants.ACTION_NONE)
            return; 
        JList list = (JList)source;
        try{
            Object data = t.getTransferData(flavor);
            ((DefaultListModel)list.getModel()).removeElement(data);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
