package santhosh;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public interface TransferNotifier{
    public static DataFlavor NOTIFICATION_FLAVOR =
            new DataFlavor(TransferNotifier.class, TransferNotifier.class.getName());

    public void transferAccepted();
    public void transferRejected();
}
