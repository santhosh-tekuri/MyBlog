package santhosh;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
class CutAction extends AbstractAction{
    JTextComponent comp;

    public CutAction(JTextComponent comp){
        super("Cut");
        this.comp = comp;
    }

    public void actionPerformed(ActionEvent e){
        comp.cut();
    }

    public boolean isEnabled(){
        return comp.isEditable()
                && comp.isEnabled()
                && comp.getSelectedText()!=null;
    }
}

// @author Santhosh Kumar T - santhosh@in.fiorano.com
class PasteAction extends AbstractAction{
    JTextComponent comp;

    public PasteAction(JTextComponent comp){
        super("Paste");
        this.comp = comp;
    }

    public void actionPerformed(ActionEvent e){
        comp.paste();
    }

    public boolean isEnabled(){
        if (comp.isEditable() && comp.isEnabled()){
            Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
            return contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        }else
            return false;
    }
}

// @author Santhosh Kumar T - santhosh@in.fiorano.com
class DeleteAction extends AbstractAction{
    JTextComponent comp;

    public DeleteAction(JTextComponent comp){
        super("Delete");
        this.comp = comp;
    }

    public void actionPerformed(ActionEvent e){
        comp.replaceSelection(null);
    }

    public boolean isEnabled(){
        return comp.isEditable()
                && comp.isEnabled()
                && comp.getSelectedText()!=null;
    }
}

// @author Santhosh Kumar T - santhosh@in.fiorano.com
class CopyAction extends AbstractAction{
    JTextComponent comp;

    public CopyAction(JTextComponent comp){
        super("Copy");
        this.comp = comp;
    }

    public void actionPerformed(ActionEvent e){
        comp.copy();
    }

    public boolean isEnabled(){
        return comp.isEnabled()
                && comp.getSelectedText()!=null;
    }
}

// @author Santhosh Kumar T - santhosh@in.fiorano.com
class SelectAllAction extends AbstractAction{
    JTextComponent comp;

    public SelectAllAction(JTextComponent comp){
        super("Select All");
        this.comp = comp;
    }

    public void actionPerformed(ActionEvent e){
        comp.selectAll();
    }

    public boolean isEnabled(){
        return comp.isEnabled()
                && comp.getText().length()>0;
    }
}
