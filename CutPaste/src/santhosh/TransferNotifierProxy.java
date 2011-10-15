package santhosh;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.StringSelection;
import java.awt.*;
import java.io.IOException;

public abstract class TransferNotifierProxy implements Transferable, TransferNotifier{
    Transferable delegate;

    public TransferNotifierProxy(Transferable delegate){
        this.delegate = delegate;
    }

    public DataFlavor[] getTransferDataFlavors(){
        DataFlavor delegateFlavors[] = delegate.getTransferDataFlavors();
        DataFlavor flavors[] = new DataFlavor[delegateFlavors.length + 1];
        System.arraycopy(delegateFlavors, 0, flavors, 0, delegateFlavors.length);
        flavors[flavors.length-1] = NOTIFICATION_FLAVOR;
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor){
        return flavor.equals(NOTIFICATION_FLAVOR)
                || delegate.isDataFlavorSupported(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException{
        return flavor.equals(NOTIFICATION_FLAVOR)
                ? this
                : delegate.getTransferData(flavor);
    }

    protected void clearClipBoard(){
        // how to clear the clipboard contents ?
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new EmptyTransferable(), null);
    }

    public void transferAccepted(){
        clearClipBoard();
    }

    public void transferRejected(){
        clearClipBoard();
    }
}