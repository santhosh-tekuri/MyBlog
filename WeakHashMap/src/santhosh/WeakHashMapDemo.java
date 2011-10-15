package santhosh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WeakHashMapDemo extends JFrame{
    FileSystemTreeModel model = new FileSystemTreeModel();
    public WeakHashMapDemo(){
        JPanel contents = (JPanel)getContentPane();
        contents.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JTree tree = new JTree(model);
        tree.setRootVisible(false);
        tree.setCellRenderer(new FileTreeCellRenderer());
        contents.add(new JScrollPane(tree));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        final JLabel label = new JLabel();
        JButton gc = new JButton(new AbstractAction("GC"){
            public void actionPerformed(ActionEvent e){
                int size = model.cache.size();
                System.gc();
                label.setText("Change in cache: "+size+" ----> "+model.cache.size());
            }
        });
        bottomPanel.add(label, BorderLayout.CENTER);
        bottomPanel.add(gc, BorderLayout.EAST);

        contents.add(bottomPanel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }
        new WeakHashMapDemo().setVisible(true);
    }
}
