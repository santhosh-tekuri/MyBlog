package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class ModalFrameTest{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        final JFrame mainFrame = new JFrame("Are you missing maximize button in JDialog ?? - santhosh@in.fiorano.com");
        mainFrame.getContentPane().add(new JScrollPane(new JTextArea("this is simle text area. \nyou won't be able to edit me while modal frame is visible...")));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);

        Action action = new AbstractAction("Show Modal Frame..."){
            public void actionPerformed(ActionEvent e){
                JFrame frame = new JFrame();
                frame.getContentPane().add(new JScrollPane(new JTree()));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(300, 400);
                ModalFrameUtil.showAsModal(frame, mainFrame);
                // this statement will be executed only after the modal frame is closed
//                JOptionPane.showMessageDialog(mainFrame, "modal frame closed.");
            }
        };

        mainFrame.getContentPane().add(new JButton(action), BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }
}