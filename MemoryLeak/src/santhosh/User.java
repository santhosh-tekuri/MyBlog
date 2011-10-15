package santhosh;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class User{
    private String name;
    private String email;

    public String getName(){
        return name;
    }

    public void setName(String name){
        support.firePropertyChange("name", this.name, this.name = name);
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        support.firePropertyChange("email", this.email, this.email = email);
    }

    PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pl){
        support.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl){
        System.out.println("removed");
        support.removePropertyChangeListener(pl);
    }

    public void printListenerCount(){
        System.out.println("Listener Count: "+support.getPropertyChangeListeners().length);
    }
}