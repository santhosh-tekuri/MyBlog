package santhosh;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Wrap your DocumentListner inside this class and register with Document.
 * it notifies your listener after specified delay. if they are more changes
 * occurred within this delay, it still notifies you only once for last change
 *
 * @author Santhosh Kumar T
 */
public class DelayedDocumentListener implements DocumentListener, ActionListener{
    private Timer timer = null;

    private DocumentListener delegate;
    private DocumentEvent event;

    public DelayedDocumentListener(DocumentListener delegate){
        this(delegate, 2000);
    }

    public DelayedDocumentListener(DocumentListener delegate, int delay){
        this.delegate = delegate;
        timer = new Timer(delay, this);
    }

    private void documentChanged(DocumentEvent de){
        timer.restart(); // Restart the timer which starts the calls back after the specified delay.
        event = de;
    }

    /*-------------------------------------------------[ DocumentListener ]---------------------------------------------------*/

    public void changedUpdate(DocumentEvent e){
        documentChanged(e);
    }

    public void insertUpdate(DocumentEvent e){
        documentChanged(e);
    }

    public void removeUpdate(DocumentEvent e){
        documentChanged(e);
    }

    /*-------------------------------------------------[ ActionListener ]---------------------------------------------------*/

    public void actionPerformed(ActionEvent e){
        if (timer.isRunning())
            timer.stop();

        DocumentEvent.EventType eventType = event.getType();
        if(eventType==DocumentEvent.EventType.INSERT)
            delegate.insertUpdate(event);
        else if(eventType==DocumentEvent.EventType.REMOVE)
            delegate.removeUpdate(event);
        if(eventType==DocumentEvent.EventType.CHANGE)
            delegate.changedUpdate(event);
    }
}