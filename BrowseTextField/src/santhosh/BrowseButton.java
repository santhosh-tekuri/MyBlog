package santhosh;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class BrowseButton extends JButton{
    private JTextComponent textComp;

    private BrowseButton(JTextComponent textComp, Action a){
        super(a);
        this.textComp = textComp;

        setText(null);
        if(getToolTipText()==null)
            setToolTipText((String)a.getValue(Action.NAME));
        if(getIcon()==null)
            setIcon(new ImageIcon(BrowseButton.class.getResource("dotdotdot.gif")));

        textComp.registerKeyboardAction(a,
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, KeyEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);

        // when textfield is diabled browsebutton should also gets disabled and vice versa
        textComp.addPropertyChangeListener("enabled", new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt){
                setEnabled(BrowseButton.this.textComp.isEnabled());
            }
        });
    }

    // overriden to make it not to occupy more height than textfield
    public Dimension getPreferredSize(){
        Dimension size = super.getPreferredSize();
        size.height = 0; // trick
        return size;
    }

    // overridden to change the action source to text component
    protected void fireActionPerformed(ActionEvent event){
        Object[] listeners = listenerList.getListenerList();
        ActionEvent e = null;
        for(int i = listeners.length-2; i>=0; i -= 2){
            if(listeners[i]==ActionListener.class){
                if(e==null){
                    String actionCommand = event.getActionCommand();
                    if(actionCommand==null)
                        actionCommand = getActionCommand();
                    e = new ActionEvent(textComp,
                            ActionEvent.ACTION_PERFORMED,
                            actionCommand,
                            event.getWhen(),
                            event.getModifiers());
                }
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }
        }
    }

    /*-------------------------------------------------[ usage ]---------------------------------------------------*/

    public static JComponent createBrowsePanel(JTextComponent tf, Action browseAction){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(UIManager.getBorder("ComboBox.border"));
        Insets insets = tf.getMargin();
        tf.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));
        panel.add(tf, BorderLayout.CENTER);
        JButton button = new BrowseButton(tf, browseAction);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setDefaultCapable(false);
        button.setFocusPainted(false);
        button.setFocusable(false);
        panel.add(button, BorderLayout.EAST);
        return panel;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JTextField textField = new JTextField();
        JOptionPane.showInputDialog(BrowseButton.createBrowsePanel(textField, new BrowseFileAction()));
    }
}