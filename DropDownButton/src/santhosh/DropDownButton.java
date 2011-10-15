package santhosh;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// @author  santhosh kumar - santhosh@in.fiorano.com
public abstract class DropDownButton extends JButton implements ChangeListener, PopupMenuListener, ActionListener, PropertyChangeListener{
    private final JButton mainButton = this;
    private final JButton arrowButton = new JButton(new ImageIcon(getClass().getResource("dropdown.gif")));

    private boolean popupVisible = false;

    public DropDownButton(){
        mainButton.getModel().addChangeListener(this);
        arrowButton.getModel().addChangeListener(this);
        arrowButton.addActionListener(this);
        arrowButton.setMargin(new Insets(3, 0, 3, 0));
        mainButton.addPropertyChangeListener("enabled", this); //NOI18N
    }

    /*-------------------------------------------------[ PropertyChangeListener ]---------------------------------------------------*/

    public void propertyChange(PropertyChangeEvent evt){
        arrowButton.setEnabled(mainButton.isEnabled());
    }

    /*-------------------------------------------------[ ChangeListener ]---------------------------------------------------*/

    public void stateChanged(ChangeEvent e){
        if(e.getSource()==mainButton.getModel()){
            if(popupVisible && !mainButton.getModel().isRollover()){
                mainButton.getModel().setRollover(true);
                return;
            }
            arrowButton.getModel().setRollover(mainButton.getModel().isRollover());
            arrowButton.setSelected(mainButton.getModel().isArmed() && mainButton.getModel().isPressed());
        }else{
            if(popupVisible && !arrowButton.getModel().isSelected()){
                arrowButton.getModel().setSelected(true);
                return;
            }
            mainButton.getModel().setRollover(arrowButton.getModel().isRollover());
        }
    }

    /*-------------------------------------------------[ ActionListener ]---------------------------------------------------*/

    public void actionPerformed(ActionEvent ae){
         JPopupMenu popup = getPopupMenu();
         popup.addPopupMenuListener(this);
         popup.show(mainButton, 0, mainButton.getHeight());
     }

    /*-------------------------------------------------[ PopupMenuListener ]---------------------------------------------------*/

    public void popupMenuWillBecomeVisible(PopupMenuEvent e){
        popupVisible = true;
        mainButton.getModel().setRollover(true);
        arrowButton.getModel().setSelected(true);
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
        popupVisible = false;

        mainButton.getModel().setRollover(false);
        arrowButton.getModel().setSelected(false);
        ((JPopupMenu)e.getSource()).removePopupMenuListener(this);
    }

    public void popupMenuCanceled(PopupMenuEvent e){
        popupVisible = false;
    }

    /*-------------------------------------------------[ Other Methods ]---------------------------------------------------*/

    protected abstract JPopupMenu getPopupMenu();

    public JButton addToToolBar(JToolBar toolbar){
        JToolBar tempBar = new JToolBar();
//           tempBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tempBar.setAlignmentX(0.5f);
        tempBar.setRollover(true);
        tempBar.add(mainButton);
        tempBar.add(arrowButton);
        tempBar.setFloatable(false);
        toolbar.add(tempBar);
        return mainButton;
    }

    /*-------------------------------------------------[ Testing ]---------------------------------------------------*/

    public static Action createAction(String name){
        return new AbstractAction(name){
            {
                putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(getValue(Action.NAME)+".gif")));
            }
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog((Component)e.getSource(), getValue(Action.NAME)+" invoked.");
            }
        };
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        JToolBar toolbar = new JToolBar();
        toolbar.putClientProperty("JToolBar.isRollover", Boolean.TRUE); //NOI18N

        DropDownButton dropdown = new DropDownButton(){
            protected JPopupMenu getPopupMenu(){
                JPopupMenu popup = new JPopupMenu();
                popup.add(getAction());
                popup.add(createAction("copy"));
                popup.add(createAction("delete"));
                return popup;
            }
        };
        dropdown.putClientProperty("hideActionText", Boolean.TRUE);
        dropdown.setAction(createAction("cut"));

        dropdown.addToToolBar(toolbar);
        JFrame frame = new JFrame("DropDownButton - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(toolbar, BorderLayout.NORTH);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

}
