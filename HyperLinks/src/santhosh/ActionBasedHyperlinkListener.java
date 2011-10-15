package santhosh;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.awt.event.ActionEvent;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ActionBasedHyperlinkListener implements HyperlinkListener{
    ActionMap actionMap;

    public ActionBasedHyperlinkListener(ActionMap actionMap){
        this.actionMap = actionMap;
    }

    public void hyperlinkUpdate(HyperlinkEvent e){
        if(e.getEventType()!=HyperlinkEvent.EventType.ACTIVATED)
            return;
        String href = e.getDescription();
        Action action = actionMap.get(href);
        if(action!=null)
            action.actionPerformed(new ActionEvent(e, ActionEvent.ACTION_PERFORMED, href));
    }
}
