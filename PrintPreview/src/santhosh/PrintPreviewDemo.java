package santhosh;

import javax.swing.*;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.text.MessageFormat;

public class PrintPreviewDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        final JTable table = new JTable(25, 3);
        final Printable printable = table.getPrintable(JTable.PrintMode.FIT_WIDTH, new MessageFormat(""), new MessageFormat(""));

        final JFrame frame = new JFrame();
        frame.getContentPane().add(new JScrollPane(table));

        Action previewAction = new AbstractAction("Print Preview"){
            int index;

            public void actionPerformed(ActionEvent e){
                PrinterJob prnJob = PrinterJob.getPrinterJob();
                PageFormat pageFormat = prnJob.defaultPage();
                JDialog dlg = new JDialog(frame, false);
                dlg.getContentPane().add(new JScrollPane(new PreviewPage(printable, pageFormat, index++)));
                dlg.pack();
                dlg.show();
            }
        };

        Action printAction = new AbstractAction("Print"){
            public void actionPerformed(ActionEvent e){
                final PrinterJob job = PrinterJob.getPrinterJob();
                if(job.printDialog()){
                    Thread printThread = new Thread(new Runnable(){
                        public void run(){
                            try{
                                PageFormat pFormat = job.defaultPage();
                                pFormat.setOrientation(PageFormat.PORTRAIT);
                                job.setPrintable(printable, pFormat);
                                job.print();
                            } catch(PrinterException e){
                                e.printStackTrace();
                            }
                        }
                    });
                    printThread.start();
                }
            }
        };

        frame.getContentPane().add(new JButton(previewAction), BorderLayout.SOUTH);
        frame.getContentPane().add(new JButton(printAction), BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();

    }
}
