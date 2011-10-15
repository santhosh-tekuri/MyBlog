package santhosh;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.awt.event.ActionEvent;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class GloballyContextSensitiveAction implements Action{
    String actionName, shortDescription, longDescription;
    Action delegate;
    JComponent source;

    private PropertyChangeListener focusOwnerListener = new PropertyChangeListener(){
        public void propertyChange(PropertyChangeEvent evt){
            if("focusOwner".equals(evt.getPropertyName())){
                if(evt.getNewValue() instanceof JComponent){
                    JComponent comp = (JComponent)evt.getNewValue();
                    Action action = comp.getActionMap().get(actionName);
                    if(action!=null)
                        changeDelegate(comp, action);
                }
            }
        }
    };

    public GloballyContextSensitiveAction(String actionName, String shortDescription, String longDescription){
        this.actionName = actionName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;

        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

        // instead of registering direclty use weak listener
        // focusManager.addPropertyChangeListener(focusOwnerListener);

        focusManager.addPropertyChangeListener(
                new WeakPropertyChangeListener(focusOwnerListener, KeyboardFocusManager.getCurrentKeyboardFocusManager()));
    }


    /*-------------------------------------------------[ Delegate Listener ]---------------------------------------------------*/

    PropertyChangeSupport support = new PropertyChangeSupport(this);

    private PropertyChangeListener delegateListener = new PropertyChangeListener(){
        public void propertyChange(PropertyChangeEvent evt){
            support.firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        }
    };

    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        support.removePropertyChangeListener(listener);
    }

    private void changeDelegate(JComponent comp, Action action){
        if(delegate!=null)
            delegate.removePropertyChangeListener(delegateListener);
        source = comp;
        delegate = action;
        delegate.addPropertyChangeListener(delegateListener);
        boolean enabled = delegate.isEnabled();
        support.firePropertyChange("enabled", !enabled, enabled);
    }

    public boolean isEnabled(){
        return delegate!=null && delegate.isEnabled();
    }

    public void setEnabled(boolean enabled){
        if(delegate!=null)
            delegate.setEnabled(enabled);
    }

    public Object getValue(String key){
        if(key.equals(Action.NAME))
            return actionName;
        else if(key.equals(Action.SHORT_DESCRIPTION))
            return shortDescription;
        else if(key.equals(Action.LONG_DESCRIPTION))
            return longDescription;
        else if(delegate!=null)
            return delegate.getValue(key);
        else
            return null;
    }

    public void putValue(String key, Object value){
        if(key.equals(Action.NAME))
            actionName = (String)value;
        else if(key.equals(Action.SHORT_DESCRIPTION))
            shortDescription = (String)value;
        else if(key.equals(Action.LONG_DESCRIPTION))
            longDescription = (String)value;
        else if(delegate!=null)
            delegate.putValue(key, value);
    }

    public void actionPerformed(ActionEvent ae){
        if(delegate!=null)
            delegate.actionPerformed(new ActionEvent(source, ae.getID(), ae.getActionCommand(), ae.getWhen(), ae.getModifiers()));
    }
}