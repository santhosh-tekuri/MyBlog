package santhosh;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Feb 19, 2006
 * Time: 12:15:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class FitTableColumns1Demo{
    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JTable table = new JTable(new Object[][]{
                {"[xxxxxxxxxxxxxxxxxxxxxxxxxxxxx]", "[yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy]", "[zzzzzzzzzzzzzzzzzzzzzzzzzzzz]"},
                {"[xxxxxxxxxxxxxx]", "[yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy]", "[zzzzzzzzzzzzzzzzzzzzzzz]"},
                {"[xxxxxxxxxx]", "[yyyyyyyyyy]", "[zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz]"}
        },
                new String[]{"col1", "col2", "col3"});

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.registerKeyboardAction(new FitTableColumnsAction()
                , KeyStroke.getKeyStroke(KeyEvent.VK_ADD, KeyEvent.CTRL_MASK)
                , JComponent.WHEN_FOCUSED);
        JFrame frame = new JFrame("Action to Fit TableColumns - santhosh@in.fiorano.com");
        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}
