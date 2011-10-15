package santhosh;

import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class HyperlinkActivator implements HyperlinkListener{
    public void hyperlinkUpdate(HyperlinkEvent e){
        if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED){
            try{
                ((JEditorPane)e.getSource()).setPage(e.getURL());
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
