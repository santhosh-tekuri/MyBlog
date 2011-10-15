package santhosh;

import java.awt.*;
import java.awt.datatransfer.*;
import java.util.Stack;
import java.util.Vector;
import java.io.IOException;

public class MyFlavorListener implements FlavorListener{
    public static int size = 10;
    public static final Vector<String> contents = new Vector<String>();

    static{
        Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new MyFlavorListener());
    }

    private MyFlavorListener(){}

    public void flavorsChanged(FlavorEvent fe){
        Clipboard clipboard =  (Clipboard)fe.getSource();
        Transferable transferable = clipboard.getContents(this);
        if(transferable!=null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
            try{
                contents.add(0, (String)transferable.getTransferData(DataFlavor.stringFlavor));
                System.err.println("data added:"+contents.get(0));
            } catch(UnsupportedFlavorException e){
                e.printStackTrace(); // impossible
            } catch(IOException e){
                e.printStackTrace();
            }
            if(contents.size()>size)
                contents.setSize(size);
        }
    }
}