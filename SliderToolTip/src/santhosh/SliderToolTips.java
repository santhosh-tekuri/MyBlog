package santhosh;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.*;

public class SliderToolTips{

    public static void enableSliderToolTips(final JSlider slider){
        slider.addChangeListener(new ChangeListener(){
            private boolean adjusting = false;
            private String oldTooltip;
            public void stateChanged(ChangeEvent e){
                if(slider.getModel().getValueIsAdjusting()){
                    if(!adjusting){
                        oldTooltip = slider.getToolTipText();
                        adjusting = true;
                    }
                    slider.setToolTipText(String.valueOf(slider.getValue()));
                    hideToolTip(slider); // to avoid flickering :)
                    postToolTip(slider);
                }else{
                    hideToolTip(slider);
                    slider.setToolTipText(oldTooltip);
                    adjusting = false;
                    oldTooltip = null;
                }
            }
        });
    }

    /*-------------------------------------------------[ Manual ToolTips ]---------------------------------------------------*/

    public static void postToolTip(JComponent comp){
        Action action = comp.getActionMap().get("postTip");
        if(action==null) // no tooltip
            return;
        ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED,
                                "postTip", EventQueue.getMostRecentEventTime(), 0);
        action.actionPerformed(ae);
    }

    public static void hideToolTip(JComponent comp){
        Action action = comp.getActionMap().get("hideTip");
        if(action==null) // no tooltip
            return;
        ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED,
                                "hideTip", EventQueue.getMostRecentEventTime(), 0);
        action.actionPerformed(ae);
    }

    /*-------------------------------------------------[ Testing ]---------------------------------------------------*/

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JSlider slider = new JSlider();
        slider.setToolTipText("drag the thumb to see tooltips!!");
        SliderToolTips.enableSliderToolTips(slider);
        JOptionPane.showMessageDialog(null, slider, "JSlider ToolTip - santhosh@in.fiorano.com", JOptionPane.INFORMATION_MESSAGE);
    }
}