package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Santhosh Kumar T
 */
public class ResizableComponentDemo{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ex){
            //ignore;
        }

        JFrame frame = new JFrame("Resizable Components - santhosh@fiorano.com");
        System.out.println(new JDesktopPane().getLayout());

        final JPanel panel = new JPanel(null);
        final JPopupMenu popup = new JPopupMenu();
        final Point location = new Point();

        abstract class AddComponentAction extends AbstractAction{
            public AddComponentAction(String name){
                super(name);
            }

            protected abstract Component createComponent();

            public void actionPerformed(ActionEvent ae){
                Component comp = createComponent();
                Dimension bounds = comp.getPreferredSize();
                JResizer resizer = new JResizer(comp);
                resizer.setBounds(location.x, location.y, bounds.width, bounds.height);
                panel.add(resizer);
                panel.repaint();
                resizer.invalidate();
                panel.revalidate();
            }
        }
        popup.add(new AddComponentAction("Add Tree"){
            protected Component createComponent(){
                return new JTree();
            }
        });
        popup.add(new AddComponentAction("Add Table"){
            protected Component createComponent(){
                JTable table = new JTable(new Object[][]{
                        {"[xxxxxxxxxxxxxxxxxxxxxxxxxxxxx]", "[yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy]", "[zzzzzzzzzzzzzzzzzzzzzzzzzzzz]"},
                        {"[xxxxxxxxxxxxxx]", "[yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy]", "[zzzzzzzzzzzzzzzzzzzzzzz]"},
                        {"[xxxxxxxxxx]", "[yyyyyyyyyy]", "[zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz]"}
                },
                        new String[]{"col1", "col2", "col3"});
                table.setPreferredScrollableViewportSize(new Dimension(300, 200));
                return new JScrollPane(table);
            }
        });

        panel.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me){
                showPopup(me);
            }

            public void mouseReleased(MouseEvent me){
                showPopup(me);
            }

            private void showPopup(MouseEvent me){
                if(me.isPopupTrigger()){
                    location.setLocation(me.getPoint());
                    popup.show(panel, me.getX(), me.getY());
                }
            }
        });

        frame.getContentPane().add(new JLabel("Right Click and from popup, select the component to add", JLabel.CENTER), BorderLayout.NORTH);
        frame.getContentPane().add(panel);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screen.width-100, screen.height-100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.show();
    }
}
