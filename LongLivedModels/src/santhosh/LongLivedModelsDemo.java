package santhosh;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;

public class LongLivedModelsDemo{
    public static void setVisibleRowCount(JTable table, int rows){
        table.setPreferredScrollableViewportSize(new Dimension(
                table.getPreferredScrollableViewportSize().width,
                rows*table.getRowHeight()
        ));
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ex){
            ex.printStackTrace();
        }
        final DefaultListModel listModel = new DefaultListModel();

        final TableModel model = new DefaultTableModel(
                new String[]{"Name", "Type", "Modified"}
                , 10
        ){
            public void addTableModelListener(TableModelListener l){
                l = new WeakTableModelListener(l, this);
                super.addTableModelListener(l);
                listModel.addElement(l.getClass().getName()+"@"+System.identityHashCode(l));
            }

            public void removeTableModelListener(TableModelListener l){
                super.removeTableModelListener(l);
                listModel.removeElement(l.getClass().getName()+"@"+System.identityHashCode(l));
            }
        };

        JFrame frame = new JFrame("Long-Lived Models may cause memory leaks - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = new JTable(model);
        setVisibleRowCount(table, 10);
        frame.getContentPane().add(new JScrollPane(table));
        Action showDialogAction = new AbstractAction("Show Dialog..."){
            public void actionPerformed(ActionEvent e){
                Window window = (Window)SwingUtilities.getRoot((Component)e.getSource());
                JDialog dlg = new JDialog((Frame)window, false); //non modal dialog
                JTable table = new JTable(model);
                setVisibleRowCount(table, 10);
                dlg.getContentPane().add(new JScrollPane(table));
                dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dlg.pack();

                // some memory leak is noticed in swing.
                // it still hold the reference to the last dialog
                // some where.
                // if you uncomment this, you will always
                // notice once extra listener than expected
                dlg.addWindowListener(new WindowAdapter(){
                    public void windowClosed(WindowEvent e){
                        JDialog dlg = (JDialog)e.getWindow();
                        dlg.getContentPane().removeAll();
                    }
                });

                dlg.setVisible(true);
            }
        };
        Action gcAction = new AbstractAction("Run GC"){
            public void actionPerformed(ActionEvent e){
                System.gc();
            }
        };
        JPanel buttons = new JPanel();
        JScrollPane comp = new JScrollPane(new JList(listModel));
        comp.setColumnHeaderView(new JLabel("TableModelListeners"));
        buttons.add(comp);
        buttons.add(new JButton(showDialogAction));
        buttons.add(new JButton(gcAction));
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
// @author Santhosh Kumar T - santhosh@in.fiorano.com
class WeakTableModelListener implements TableModelListener{
    WeakReference listenerRef;
    TableModel src;

    public WeakTableModelListener(TableModelListener listener, TableModel src){
        // listenerRef = new WeakReference(listener);

        // using tablelistenerref rather than weakreference
        // will remove the tablemodellistener as soon as
        // the listener gets gced
        listenerRef = new TableListenerRef(listener);
        this.src = src;
    }

    public void tableChanged(TableModelEvent evt) {
      TableModelListener listener = (TableModelListener)listenerRef.get();
      if(listener==null)
          src.removeTableModelListener(this);
      else
        listener.tableChanged(evt);
    }

    class TableListenerRef extends WeakReference implements Runnable{
        public TableListenerRef(Object o){
            super(o, ActiveReferenceQueue.getInstance());
        }

        public void run(){
            src.removeTableModelListener(WeakTableModelListener.this);
        }
    }
}

class ActiveReferenceQueue extends ReferenceQueue implements Runnable{
    private static ActiveReferenceQueue singleton = null;

    public static ActiveReferenceQueue getInstance(){
        if(singleton==null)
            singleton = new ActiveReferenceQueue();
        return singleton;
    }

    private ActiveReferenceQueue(){
        Thread t = new Thread(this, "ActiveReferenceQueue");
        t.setDaemon(false);
        t.start();
    }

    public void run(){
        for(;;){
            try{
                Reference ref = super.remove(0);
                if(ref instanceof Runnable){
                    try{
                        ((Runnable)ref).run();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

