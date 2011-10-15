package santhosh;

import javax.swing.*;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.*;

public class PreviewPage extends JComponent{
    private Printable printable;
    private PageFormat pageFormat;
    private int pageIndex;

    public PreviewPage(Printable printable, PageFormat pageFormat, int pageIndex){
        this.printable = printable;
        this.pageFormat = pageFormat;
        this.pageIndex = pageIndex;
        setBackground(Color.white);
    }

    public Dimension getPreferredSize(){
        return new Dimension((int)pageFormat.getWidth(), (int)pageFormat.getHeight());
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        try{
            printable.print(g, pageFormat, pageIndex);
        } catch(PrinterException ex){
            throw new RuntimeException(ex);
        }
    }
}
