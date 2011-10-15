package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.HashMap;

public class DNDChallenge extends JFrame{
    ListCellRenderer renderer = new DefaultListCellRenderer(){
        Map icons/*<Class, Icon>*/ = new HashMap(/*<Class, Icon>*/);

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Class clazz = value.getClass();
            Icon icon = (Icon)icons.get(clazz);
            if(icon==null)
                icons.put(clazz, icon = new ImageIcon(clazz.getResource(getSimpleClassName(clazz)+".gif")));
            setIcon(icon);
            return this;
        }
    };

    protected JList personList = new JList(createListModel(Person.class));
    protected JList employeeList = new JList(createListModel(Employee.class));
    protected JList managerList = new JList(createListModel(Manager.class));
    JTextArea textArea = new JTextArea(5, 5);

    public DNDChallenge(){
        super("DataFlavors & Transferables - santhosh@in.fiorano.com");

        JPanel listPanel = new JPanel(new GridLayout(1, 0));
        listPanel.add(createScroll(personList, "Persons"));
        listPanel.add(createScroll(employeeList, "Employees"));
        listPanel.add(createScroll(managerList, "Managers"));

        Container contents = getContentPane();
        contents.add(listPanel, BorderLayout.CENTER);
        contents.add(createScroll(textArea, "Editor"), BorderLayout.SOUTH);

        applyDNDHack(personList);
        applyDNDHack(employeeList);
        applyDNDHack(managerList);

        personList.setCellRenderer(renderer);
        employeeList.setCellRenderer(renderer);
        managerList.setCellRenderer(renderer);

        personList.setDragEnabled(true);
        employeeList.setDragEnabled(true);
        managerList.setDragEnabled(true);

        setSize(500, 300);
    }

    private JScrollPane createScroll(JComponent c, String title){
        JScrollPane scroll = new JScrollPane(c);
        JLabel label = new JLabel("  "+title);
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        scroll.setColumnHeaderView(label);
        return scroll;
    }

    private String getSimpleClassName(Class clazz){
        String name = clazz.getName();
        int index = name.lastIndexOf('.');
        return index!=-1 ? name.substring(index+1) : name;
    }

    private ListModel createListModel(Class clazz){
        try{
            String name = getSimpleClassName(clazz);
            Constructor constructor = clazz.getConstructor(new Class[]{String.class});
            DefaultListModel model = new DefaultListModel();
            for(int i = 0; i<5; i++)
                model.addElement(constructor.newInstance(new Object[] { name+(i+1) }));
            return model;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void applyDNDHack(JComponent comp){
        MouseListener dragListener = null;

        // the default dnd implemntation requires to first select node and then drag
        try{
            Class clazz = Class.forName("javax.swing.plaf.basic.BasicDragGestureRecognizer");
            MouseListener[] mouseListeners = comp.getMouseListeners();
            for(int i = 0; i<mouseListeners.length; i++){
                if(clazz.isAssignableFrom(mouseListeners[i].getClass())){
                    dragListener = mouseListeners[i];
                    break;
                }
            }

            if(dragListener!=null){
                comp.removeMouseListener(dragListener);
                comp.removeMouseMotionListener((MouseMotionListener)dragListener);
                comp.addMouseListener(dragListener);
                comp.addMouseMotionListener((MouseMotionListener)dragListener);
            }
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        DNDChallenge challenge = new DNDChallenge();
        challenge.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        challenge.setVisible(true);
    }
}

