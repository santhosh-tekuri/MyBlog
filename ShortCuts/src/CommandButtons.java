
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.HashSet;

public class CommandButtons{
    public static void main(String[] args){
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(new JButton("Ok"));
        buttons.add(new JButton("Cancel"));
        buttons.add(new JButton("Apply"));
        buttons.add(new JButton("Help"));

        Set forwardKeys = new HashSet();
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
        buttons.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        Set backwardKeys = new HashSet();
        backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.SHIFT_MASK));
        buttons.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        JPanel contents = new JPanel(new BorderLayout());
        contents.add(new JButton("Center"), BorderLayout.CENTER);
        contents.add(new JButton("North"), BorderLayout.NORTH);
        contents.add(new JButton("South"), BorderLayout.SOUTH);
        contents.add(new JButton("East"), BorderLayout.EAST);
        contents.add(new JButton("West"), BorderLayout.WEST);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(contents);
        frame.getContentPane().add(buttons, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
