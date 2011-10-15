package santhosh;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class EmptyTransferable implements Transferable{
    public DataFlavor[] getTransferDataFlavors(){
        return new DataFlavor[0];
    }

    public boolean isDataFlavorSupported(DataFlavor flavor){
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException{
        throw new UnsupportedFlavorException(flavor);
    }
}
