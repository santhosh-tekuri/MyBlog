package santhosh;

import org.xml.sax.InputSource;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.StringReader;

// @author Santhosh Kumar T - santhosh@in.fiorano.com
public class XMLTreeDemo extends JFrame{
    JTree tree = new JTree();
    JTextArea editor = new JTextArea(10, 75);

    public XMLTreeDemo(){
        super("XMLViewer - santhosh@in.fiorano.com");
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(10, 10, 10, 10));
        final JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("EDITOR", new JScrollPane(editor));
        tabPane.addTab("TREE", new JScrollPane(tree));

        tree.setFont(new Font("DialogInput", Font.PLAIN, 12));
        tree.setShowsRootHandles(true);
        tree.setModel(new XMLTreeModel(null));
        tree.setCellRenderer(new XMLTreeCellRenderer());

        JPanel contents = (JPanel)getContentPane();
        contents.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contents.add(tabPane);

        tabPane.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent ce){
                if(tabPane.getSelectedIndex()==1){
                    try{
                        updateTree();
                    } catch(Exception ex){
                        tabPane.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(tabPane, ex.getMessage());
                    }
                }
            }
        });
    }

    private void updateText() throws Exception{

    }

    private void updateTree() throws Exception{
        InputSource is = new InputSource(new StringReader(editor.getText()));
        tree.setModel(new XMLTreeModel(is, true));

        int row = 0;
        while(row<tree.getRowCount())
            tree.expandRow(row++);
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){
            e.printStackTrace();
        }

        JFrame frame = new XMLTreeDemo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}