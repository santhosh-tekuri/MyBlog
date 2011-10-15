package santhosh;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.WeakListeners;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class UserNode extends AbstractNode implements PropertyChangeListener{
    User user;

    public UserNode(User user){
        super(Children.LEAF);
        this.user = user;
        user.addPropertyChangeListener(WeakListeners.propertyChange(this, user));
    }

    public void propertyChange(PropertyChangeEvent evt){
        // do something
    }
}
