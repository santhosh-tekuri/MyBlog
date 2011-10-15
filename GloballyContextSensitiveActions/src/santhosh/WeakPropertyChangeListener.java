package santhosh;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class WeakPropertyChangeListener implements PropertyChangeListener{
    WeakReference listenerRef;
    Object src;

    public WeakPropertyChangeListener(PropertyChangeListener listener, Object src){
        listenerRef = new ListenerReference(listener, this);
        this.src = src;
    }

    public void propertyChange(PropertyChangeEvent evt){
        PropertyChangeListener listener = (PropertyChangeListener)listenerRef.get();
        if(listener!=null)
            listener.propertyChange(evt);
    }

    private void removeListener(){
        try{
            Method method = src.getClass().getMethod("removePropertyChangeListener"
                    , new Class[] {PropertyChangeListener.class});
            method.invoke(src, new Object[]{ this });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    static class ListenerReference extends WeakReference{
        WeakPropertyChangeListener listener;

        public ListenerReference(Object ref, WeakPropertyChangeListener listener){
            super(ref, ActiveReferenceQueue.getInstance());
            this.listener = listener;
        }

        public void run(){
            listener.removeListener();
            listener = null;
        }
    }
}