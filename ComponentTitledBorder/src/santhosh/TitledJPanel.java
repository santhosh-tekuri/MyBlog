package santhosh;

import santhosh.ComponentTitledBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * @author Santhosh Kumar T
 */
public class TitledJPanel extends JPanel{
    private ComponentTitledBorder componentBorder;
    public TitledJPanel(JComponent comp, Border border){
        super.setBorder(componentBorder = new ComponentTitledBorder(comp, this, border));
        setToolTipText(comp.getToolTipText());
        comp.addPropertyChangeListener(TOOL_TIP_TEXT_KEY, new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt){
                setToolTipText((String)evt.getNewValue());
            }
        });
    }

    @Override
    public String getToolTipText(MouseEvent event){
        if(componentBorder.isInside(event))
            return super.getToolTipText(event);
        else
            return null;
    }
}
