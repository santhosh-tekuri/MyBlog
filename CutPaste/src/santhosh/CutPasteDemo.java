package santhosh;

import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.*;

public class CutPasteDemo{
    private static JScrollPane createList(int from, int to){
        DefaultListModel model1 = new DefaultListModel();
        for(int i = from; i<=to; i++)
            model1.addElement("User "+i);

        JList list = new JList(model1);
        list.setTransferHandler(new ListTransferHandler());
        list.setCellRenderer(new MyListCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    public static void main(String[] args){

        Clipboard clipboard =
                Toolkit.getDefaultToolkit().getSystemClipboard();
//                new Clipboard("local clipboard");
        clipboard.setContents(null, null);

        StringSelection transferable = new StringSelection("data");
        System.out.println("transferable placed: "+transferable);
        clipboard.setContents(transferable, null);
        System.out.println("transferable fetched: "+clipboard.getContents(null));

        JFrame frame = new JFrame("Deferred Cut/Paste - santhosh@in.fiorano.com");
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
                , createList(1, 5)
                , createList(6, 10));
        split.setResizeWeight(0.5d);
        frame.getContentPane().add(split);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
