package santhosh;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class MutableJListTest{
    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        DefaultMutableListModel model = new DefaultMutableListModel();
        for(int i=0; i<50; i++)
            model.addElement(new Integer(5));
        JListMutable list = new JListMutable(model);
        list.setCellRenderer(new MySlider());
        list.setListCellEditor(new MySlider());

        JFrame frame = new JFrame("Editable JList - santhosh@in.fiorano.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(new JScrollPane(list));
        frame.setSize(500, 300);
        frame.show();
    }

    static class MySlider extends AbstractCellEditor implements ListCellRenderer, ListCellEditor, ChangeListener{
        private JSlider slider = new JSlider(0, 5);

        public MySlider(){
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(1);
        }

        public void stateChanged(ChangeEvent e){
            fireEditingStopped();
        }

        public boolean isCellEditable(EventObject anEvent) {
            if(anEvent instanceof MouseEvent){
                return ((MouseEvent)anEvent).getClickCount() >= 1;
            }
	        return true;
	    }

        protected void fireEditingStopped(){
            super.fireEditingStopped();
            slider.removeChangeListener(this);
        }

        protected void fireEditingCanceled(){
            super.fireEditingCanceled();
            slider.removeChangeListener(this);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            slider.setValue(((Integer)value).intValue());
            return slider;
        }
        
        public Object getCellEditorValue(){
            return new Integer(slider.getValue());
        }

        public Component getListCellEditorComponent(JList list, Object value, boolean isSelected, int index){
            slider.setValue(((Integer)value).intValue());
            slider.addChangeListener(this);
            return slider;
        }
    }

//    public static void main(String[] args){
//        try{
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//        DefaultMutableListModel model = new DefaultMutableListModel();
//        for(int i=0; i<50; i++)
//            model.addElement("ITEM "+(i+1));
//        model.addElement("this is longest item....");
//        JListMutable list = new JListMutable(model);
//        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//        JTextField tf = new JTextField();
//        tf.setBorder(BorderFactory.createLineBorder(Color.black));
//        list.setListCellEditor(new DefaultListCellEditor(tf));
//        list.setVisibleRowCount(-1);
//
//        JFrame frame = new JFrame("Editable JList - santhosh@in.fiorano.com");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ((JPanel)frame.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        frame.getContentPane().add(new JScrollPane(list));
//        frame.setSize(500, 300);
//        frame.show();
//    }
}
