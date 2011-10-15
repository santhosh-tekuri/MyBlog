package santhosh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;

/**
 * @author Santhosh Kumar T
 */
public class JTableEditingDemo{
    private static JTable createTable1(){
        JTable table = new JTable(2, 2);
        table.getColumn("B").setCellEditor(new DefaultCellEditor(new JComboBox(new String[]{"item1", "item2", "item3"})));
        return table;
    }

    private static JTable createTable2(){
        JTable table = new JTable(2, 2){
            private final KeyStroke tabKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                AWTEvent currentEvent = EventQueue.getCurrentEvent();
                if(currentEvent instanceof KeyEvent){
                    KeyEvent ke = (KeyEvent)currentEvent;
                    if(ke.getSource()!=this)
                        return;
                    // focus change with keyboard
                    if(rowIndex==0 && columnIndex==0
                            && KeyStroke.getKeyStrokeForEvent(ke).equals(tabKeyStroke)){
                        ((DefaultTableModel)getModel()).addRow(new Object[getColumnCount()]);
                        rowIndex = getRowCount()-1;
                    }
                }
                super.changeSelection(rowIndex, columnIndex, toggle, extend);
            }
        };

        JTextField tf = new JTextField();
        tf.setBorder(BorderFactory.createEmptyBorder());
        table.setDefaultEditor(Object.class, new DefaultCellEditor(tf));

        JComboBox combo = new JComboBox(new String[]{"item1", "item2", "item3"}){
            public void processFocusEvent(FocusEvent fe) {
                super.processFocusEvent(fe);
                Component focusOwner = KeyboardFocusManager.
                    getCurrentKeyboardFocusManager().getFocusOwner();

                if (isDisplayable() && fe.getID()==FocusEvent.FOCUS_GAINED
                        && focusOwner==this && !isPopupVisible()) {
                            showPopup();
                }
            }
        };
        combo.setBorder(BorderFactory.createEmptyBorder());
        table.getColumn("B").setCellEditor(new DefaultCellEditor(combo));
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        return table;
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new JFrame("JTable Editing Tweaks - santhosh@in.fiorano.com");
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("JTable", new JScrollPane(createTable1()));
        tabPane.addTab("MyTable", new JScrollPane(createTable2()));
        frame.getContentPane().add(tabPane);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}