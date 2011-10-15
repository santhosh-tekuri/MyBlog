package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class TextFieldsDemo extends JFrame{
    public TextFieldsDemo(){
        super("Context Menu on TextComponents - santhosh@in.fiorano.com");
        JPanel panel = (JPanel)getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("TextField:"));
        panel.add(new JTextField(25));
        panel.add(new JLabel("TextField with a custom popupmenu:"));
        JTextField tf = new JTextField(25);
        tf.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me){
                showPopup(me);
            }

            public void mouseReleased(MouseEvent me){
                showPopup(me);
            }

            private void showPopup(MouseEvent me){
                if(me.isPopupTrigger()){
                    JPopupMenu menu = new JPopupMenu();
                    menu.add("this is");
                    menu.add("custom");
                    menu.add("popupmenu");
                    menu.show(me.getComponent(), me.getX(), me.getY());
                }
            }
        });
        panel.add(tf);
        panel.add(new JButton(new AbstractAction("JOptionPane"){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showInputDialog("user name:");
            }
        }));

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
//        Toolkit.getDefaultToolkit().getSystemEventQueue().push(new MyEventQueue());
        Toolkit.getDefaultToolkit().addAWTEventListener(new TextComponentContextMenuListener(),
AWTEvent.MOUSE_EVENT_MASK);

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        new TextFieldsDemo().setVisible(true);
    }
}