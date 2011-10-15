package santhosh;

import java.awt.datatransfer.Transferable;

public interface XTransferable extends Transferable{
    public void transferAccepted();
    public void transferRejected();
}
