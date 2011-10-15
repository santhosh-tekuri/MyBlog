package santhosh;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConfirmFileOverWriteAction extends AbstractAction{

    public void actionPerformed(ActionEvent e){
        System.err.println("");
    }

    public static void main(String[] args){
        JFileChooser chooser = new JFileChooser();
        chooser.addActionListener(new ConfirmFileOverWriteAction());
        chooser.setFileFilter(null);
        chooser.showSaveDialog(null);
    }
}
