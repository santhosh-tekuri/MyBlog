package santhosh;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ObjectTransferable implements Transferable {
    private Object obj;
    private DataFlavor flavor;

    public ObjectTransferable (Object obj) {
        this.obj = obj;
        flavor = new ObjectFlavor(obj.getClass());
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { flavor };
    }

    public boolean isDataFlavorSupported (DataFlavor flavor) {
        if(this.flavor.equals(flavor))
            return true;
        return false;
    }

    public Object getTransferData (DataFlavor flavor) throws UnsupportedFlavorException{
        if(isDataFlavorSupported(flavor))
            return obj;
        throw new UnsupportedFlavorException (flavor);
    }
}