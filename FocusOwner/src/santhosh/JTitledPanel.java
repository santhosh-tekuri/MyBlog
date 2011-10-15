package santhosh;

import javax.swing.*;
import java.awt.*;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class JTitledPanel extends JComponent{
    JLabel titleLabel = new JLabel();

    FocusOwnerTracker tracker = new FocusOwnerTracker(this){
        public void focusGained(){
            titleLabel.setForeground(UIManager.getColor("textHighlightText"));
            titleLabel.setBackground(UIManager.getColor("textHighlight"));
        }

        public void focusLost(){
            titleLabel.setForeground(UIManager.getColor("textText"));
            titleLabel.setBackground(UIManager.getColor("control").darker());
        }
    };

    JPanel contents = new JPanel(new BorderLayout());

    public JTitledPanel(String title){
        setLayout(new BorderLayout());
        titleLabel.setText(title);
        titleLabel.setOpaque(true);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        tracker.focusLost();

        add(titleLabel, BorderLayout.NORTH);
        add(contents, BorderLayout.CENTER);

        // Memory-Leak occurs here. Why ?
        // How to avoid this ?
        tracker.start();
    }

    public JPanel getContents(){
        return contents;
    }
}
//    public void addNotify(){
//        super.addNotify();
//        tracker.start();
//    }
//
//    public void removeNotify(){
//        super.removeNotify();
//        tracker.stop();
//    }
//}