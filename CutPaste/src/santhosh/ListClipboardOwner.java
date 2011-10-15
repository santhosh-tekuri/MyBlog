package santhosh;

import javax.swing.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;

public class ListClipboardOwner implements ClipboardOwner{
    // used as JList's client property
    public static final String CLIP_BOARD_OWNER = "ClipBoardOwner";

    private JList list;
    private int index;

    public ListClipboardOwner(JList list){
        this.list = list;
        index = list.getSelectedIndex();
        list.putClientProperty(CLIP_BOARD_OWNER, this);
        list.paintImmediately(list.getCellBounds(index, index));
    }

    public int getIndex(){
        return index;
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents){
        if(list.getClientProperty(CLIP_BOARD_OWNER)==this)
            list.putClientProperty(CLIP_BOARD_OWNER, null);
        list.paintImmediately(list.getCellBounds(index, index));
    }
}