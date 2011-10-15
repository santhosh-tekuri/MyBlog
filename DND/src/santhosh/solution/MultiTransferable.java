package santhosh.solution;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

/**
 * wrapps multiple transferable into a single transferable
 * if your component requires to support multiple transferable, you
 * can create MultiTransferable from those flavors and
 * return them in your TransferHanlder.createTransferFloavor
 *
 * @author Santhosh Kumar T
 */
public class MultiTransferable implements Transferable{
    private final Transferable transferable[];

    public MultiTransferable(Transferable[] transferable){
        this.transferable = transferable;
    }

    public DataFlavor[] getTransferDataFlavors(){
        // used Set rather than list, so that we don't return the same
        // flavor more than once
        Set set = new HashSet();
        for(int i = 0; i<transferable.length; i++){
            DataFlavor[] flavors = transferable[i].getTransferDataFlavors();
            for(int j = 0; j<flavors.length; j++)
                set.addAll(Arrays.asList(flavors));
        }
        return (DataFlavor[])set.toArray(new DataFlavor[set.size()]);
    }

    public boolean isDataFlavorSupported(DataFlavor flavor){
        for(int i = 0; i<transferable.length; i++){
            if(transferable[i].isDataFlavorSupported(flavor))
                return true;
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException{
        for(int i = 0; i<transferable.length; i++){
            if(transferable[i].isDataFlavorSupported(flavor))
                return transferable[i].getTransferData(flavor);
        }
        throw new UnsupportedFlavorException(flavor);
    }
}