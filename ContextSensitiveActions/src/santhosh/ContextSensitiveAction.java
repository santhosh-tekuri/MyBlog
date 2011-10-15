package santhosh;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ContextSensitiveAction implements Action{
    protected Action delegate;
    protected Object source;

    public ContextSensitiveAction(Action delegate, Object source){
        this.delegate = delegate;
        this.source = source;
    }

    public boolean isEnabled(){
        return delegate.isEnabled();
    }

    public void setEnabled(boolean enabled){
        delegate.setEnabled(enabled);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        delegate.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        delegate.removePropertyChangeListener(listener);
    }

    public Object getValue(String key){
        return delegate.getValue(key);
    }

    public void putValue(String key, Object value){
        delegate.putValue(key, value);
    }

    public void actionPerformed(ActionEvent ae){
        delegate.actionPerformed(new ActionEvent(source, ae.getID(), ae.getActionCommand(), ae.getWhen(), ae.getModifiers()));
    }
}