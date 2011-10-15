package santhosh;

import javax.swing.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ListTransferHandler extends TransferHandler{
    protected Transferable createTransferable(JComponent c){
        JList list = (JList)c;
        return new ListItemSelection(list);
    }

    public void exportToClipboard(JComponent comp, Clipboard clip, int action) throws IllegalStateException{
        int clipboardAction = getSourceActions(comp) & action;
        if(clipboardAction!=NONE){
            Transferable t = createTransferable(comp);
            if(t!=null){
                try{
                    clip.setContents(t, new ListClipboardOwner((JList)comp));
                    exportDone(comp, t, clipboardAction);
                    return;
                } catch(IllegalStateException ise){
                    exportDone(comp, t, NONE);
                    throw ise;
                }
            }
        }
        exportDone(comp, null, NONE);
    }

    public int getSourceActions(JComponent c){
        return MOVE;
    }

    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors){
        for(int i = 0; i<transferFlavors.length; i++){
            if(transferFlavors[i].equals(DataFlavor.stringFlavor))
                return true;
        }
        return false;
    }

    public boolean importData(JComponent comp, Transferable t){
        try{
            String data = String.valueOf(t.getTransferData(DataFlavor.stringFlavor));
            JList list = (JList)comp;
            ((DefaultListModel)list.getModel()).addElement(data);
            acceptOrReject(t, true);
            return true;
        } catch(Exception e){
            acceptOrReject(t, false);
            return false;
        }
    }

    private void acceptOrReject(Transferable t, boolean accept){
        try{
            if(t.isDataFlavorSupported(TransferNotifier.NOTIFICATION_FLAVOR)){
                TransferNotifier notifier = (TransferNotifier)t.getTransferData(TransferNotifier.NOTIFICATION_FLAVOR);
                if(accept)
                    notifier.transferAccepted();
                else
                    notifier.transferRejected();
            }
        } catch(UnsupportedFlavorException e){
            e.printStackTrace(); // impossible
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}